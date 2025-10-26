package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Document(collection = "plots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plot {
    @Id
    @Schema(description = "Unique identifier for the plot", example = "plot123")
    private String id;

    @Schema(description = "Farm id this plot belongs to", example = "farm123")
    private String farmId;

    @Schema(description = "Human friendly name of the plot", example = "North Field A")
    private String name;

    @Schema(description = "Area in hectares", example = "1.5")
    private Double area;

    @Schema(description = "Optional geo or description of location")
    private String location;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Plot id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public Plot farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Plot name(String name) {
        setName(name);
        return this;
    }

    public Double getArea() {
        return this.area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Plot area(Double area) {
        setArea(area);
        return this;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Plot location(String location) {
        setLocation(location);
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Plot createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }


}
