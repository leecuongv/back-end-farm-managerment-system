package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "auditLogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {
    @Id
    private String id;
    private String userId;
    private String action;
    private String entity; // e.g., "FinancialTransaction", "InventoryItem"
    private String entityId;
    private LocalDateTime timestamp;
    private String details;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuditLog id(String id) {
        setId(id);
        return this;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AuditLog userId(String userId) {
        setUserId(userId);
        return this;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public AuditLog action(String action) {
        setAction(action);
        return this;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public AuditLog entity(String entity) {
        setEntity(entity);
        return this;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public AuditLog entityId(String entityId) {
        setEntityId(entityId);
        return this;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public AuditLog timestamp(LocalDateTime timestamp) {
        setTimestamp(timestamp);
        return this;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public AuditLog details(String details) {
        setDetails(details);
        return this;
    }


}
