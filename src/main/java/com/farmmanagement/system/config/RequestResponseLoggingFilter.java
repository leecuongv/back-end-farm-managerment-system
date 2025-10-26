package com.farmmanagement.system.config;

import com.farmmanagement.system.service.AuditService;
import com.farmmanagement.system.security.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Logs request and response bodies for auditing. Writes a summary into AuditService.
 * Note: Be careful with sensitive data (passwords). This implementation logs bodies as-is.
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private final AuditService auditService;
    private static final ObjectMapper mapper = new ObjectMapper();

    public RequestResponseLoggingFilter(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        LocalDateTime start = LocalDateTime.now();
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            // Read request body
            String requestBody = getRequestBody(wrappedRequest);
            String responseBody = getResponseBody(wrappedResponse);

            // redact sensitive fields before writing to DB
            requestBody = redactSensitiveFields(requestBody);
            responseBody = redactSensitiveFields(responseBody);

            String method = request.getMethod();
            String path = request.getRequestURI();
            int status = wrappedResponse.getStatus();

            String userId = SecurityUtils.getCurrentUserId().orElse("anonymous");

            String details = buildDetails(start.toString(), method, path, status, request.getQueryString(), requestBody, responseBody);

            // Use action "HTTP" and entity = path for quick searching in audit logs
            auditService.logEvent(userId, "HTTP", path, null, details);

            // Important: copy response body back to the real response
            wrappedResponse.copyBodyToResponse();
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        if (buf == null || buf.length == 0) return "";
        int length = Math.min(buf.length, 1024 * 10); // limit to 10KB for safety
        return new String(buf, 0, length, StandardCharsets.UTF_8);
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] buf = response.getContentAsByteArray();
        if (buf == null || buf.length == 0) return "";
        int length = Math.min(buf.length, 1024 * 20); // limit to 20KB
        return new String(buf, 0, length, StandardCharsets.UTF_8);
    }

    private String buildDetails(String timestamp, String method, String path, int status, String query, String reqBody, String respBody) {
        StringBuilder sb = new StringBuilder();
        sb.append("timestamp=").append(timestamp).append("; ");
        sb.append("method=").append(method).append("; ");
        sb.append("path=").append(path).append("; ");
        if (query != null) sb.append("query=").append(query).append("; ");
        sb.append("status=").append(status).append("; ");
        if (reqBody != null && !reqBody.isEmpty()) sb.append("request=").append(reqBody).append("; ");
        if (respBody != null && !respBody.isEmpty()) sb.append("response=").append(respBody).append("; ");
        return sb.toString();
    }

    private String redactSensitiveFields(String body) {
        if (body == null || body.isBlank()) return body;
        try {
            JsonNode root = mapper.readTree(body);
            if (root.isObject()) {
                redactObject((ObjectNode) root);
                return mapper.writeValueAsString(root);
            }
        } catch (Exception e) {
            // not JSON or parsing failed: do a simple redaction for common tokens
            String lower = body.toLowerCase();
            if (lower.contains("authorization") || lower.contains("bearer ")) {
                return "[REDACTED]";
            }
        }
        return body;
    }

    private void redactObject(ObjectNode node) {
        var it = node.fieldNames();
        while (it.hasNext()) {
            String field = it.next();
            String lower = field.toLowerCase();
            if (lower.contains("password") || lower.contains("pass") || lower.contains("token") || lower.contains("authorization")) {
                node.put(field, "[REDACTED]");
            } else {
                JsonNode child = node.get(field);
                if (child != null && child.isObject()) {
                    redactObject((ObjectNode) child);
                }
            }
        }
    }
}
