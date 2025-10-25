package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    private String id;
    private String farmId;
    private String title;
    private String description;
    private String assignedTo; // User ID
    private TaskStatus status;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private String createdBy; // User ID


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Task id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public Task farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Task title(String title) {
        setTitle(title);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task description(String description) {
        setDescription(description);
        return this;
    }

    public String getAssignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Task assignedTo(String assignedTo) {
        setAssignedTo(assignedTo);
        return this;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Task status(TaskStatus status) {
        setStatus(status);
        return this;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Task dueDate(LocalDate dueDate) {
        setDueDate(dueDate);
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Task createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Task createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public enum TaskStatus {
        TODO,
        IN_PROGRESS,
        DONE
    }
}
