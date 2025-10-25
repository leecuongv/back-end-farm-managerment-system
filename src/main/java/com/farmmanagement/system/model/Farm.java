package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Document(collection = "farms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {
    @Id
    @Schema(description = "Unique identifier of the Farm", example = "f12345", required = true)
    private String id;
    @Schema(description = "Name of the Farm", example = "Green Valley Farm", required = true)
    private String name;
    @Schema(description = "Location of the Farm", example = "California, USA", required = true)
    private String location;
    @Schema(description = "Creation timestamp of the Farm record", example = "2023-10-01T12:00:00", required = true)
    private LocalDateTime createdAt;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Farm id(String id) {
        setId(id);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Farm name(String name) {
        setName(name);
        return this;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Farm location(String location) {
        setLocation(location);
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Farm createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

}
