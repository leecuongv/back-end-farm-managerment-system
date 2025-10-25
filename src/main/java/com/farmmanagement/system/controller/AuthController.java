package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.User;
import com.farmmanagement.system.repository.UserRepository;
import com.farmmanagement.system.security.JwtTokenService;
import com.farmmanagement.system.security.JwtTokenService.TokenDetails;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Authentication", description = "User authentication endpoints")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private JwtTokenService jwtTokenService;

    // This is a simplified login. In a real app, you'd use Spring Security, password encoding, and JWT generation.
    @Operation(summary = "Login", description = "Authenticate a user with email and password.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "LoginSuccess",
                    value = "{\"message\":\"Login successful\",\"user\":{\"id\":\"64b1d2f3e5a6c7d8e9f0a1aa\",\"email\":\"admin@example.com\",\"role\":\"ADMIN\"}}"
                )
            )
        ),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "403", description = "Account inactive")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (!user.isActive()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account not activated by administrator.");
        }

        // Simplified password check. DO NOT use in production.
        if (user.getPassword() != null && user.getPassword().equals(password)) {
            auditService.logEvent(user.getId(), "USER_LOGIN", "User", user.getId(), "User logged in successfully");
            TokenDetails tokenDetails = jwtTokenService.generateToken(user);

            Map<String, Object> userPayload = new LinkedHashMap<>();
            userPayload.put("id", user.getId());
            userPayload.put("username", user.getUsername());
            userPayload.put("email", user.getEmail());
            userPayload.put("fullName", user.getFullName());
            userPayload.put("role", user.getRole());
            userPayload.put("farmIds", user.getFarmIds() != null ? List.copyOf(user.getFarmIds()) : List.of());
            userPayload.put("authProvider", user.getAuthProvider());
            userPayload.put("isActive", user.isActive());
            userPayload.put("createdAt", user.getCreatedAt());

            Map<String, Object> responseBody = new LinkedHashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("token", tokenDetails.token());
            responseBody.put("tokenType", "Bearer");
            responseBody.put("expiresAt", tokenDetails.expiresAt().toString());
            responseBody.put("user", userPayload);

            return ResponseEntity.ok(responseBody);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    // This is a placeholder for Google Sign-In.
    @Operation(summary = "Google login", description = "Handle Google OAuth login callback.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Endpoint reached",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "GoogleLoginPending",
                    value = "{\"message\":\"Google login endpoint hit. Verification logic needed.\"}"
                )
            )
        )
    })
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
    // String idToken = payload.get("id_token");

        // In a real app, you would:
        // 1. Verify the id_token with Google's public keys.
        // 2. Extract the user's email from the verified token.
        // 3. Find the user in your database by that email.
        
        // Mock implementation: Assume token is valid and we get an email.
        // String email = "user.from.google@example.com"; // This would be extracted from the token
        // User user = userRepository.findByEmail(email).orElse(null);
        
        // if (user == null) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not registered by admin.");
        // }

        // if (!user.getIsActive()) {
        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account not activated by administrator.");
        // }
        
        // auditService.logEvent("USER_LOGIN_GOOGLE", user.getId(), "User logged in successfully with Google");
        // // Return a JWT token
        // return ResponseEntity.ok(Map.of("message", "Google login successful", "user", user));

        return ResponseEntity.ok(Map.of("message", "Google login endpoint hit. Verification logic needed."));
    }
}
