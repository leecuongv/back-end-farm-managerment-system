package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "seasons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Season {
    @Id
    @Schema(description = "Unique identifier for the season", example = "season123")
    private String id;

    @Schema(description = "Farm id this season belongs to", example = "farm123")
    private String farmId;

    @Schema(description = "Name of the season or crop cycle", example = "Winter Wheat 2025")
    private String name;

    @Schema(description = "Crop type", example = "Wheat")
    private String cropType;

    @Schema(description = "Start date of season", example = "2025-01-15")
    private LocalDate startDate;

    @Schema(description = "End date of season", example = "2025-05-30")
    private LocalDate endDate;

    @Schema(description = "List of plot ids participating in this season")
    private List<String> plotIds;

    @Schema(description = "Optional notes or description")
    private String notes;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Season id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public Season farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Season name(String name) {
        setName(name);
        return this;
    }

    public String getCropType() {
        return this.cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public Season cropType(String cropType) {
        setCropType(cropType);
        return this;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Season startDate(LocalDate startDate) {
        setStartDate(startDate);
        return this;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Season endDate(LocalDate endDate) {
        setEndDate(endDate);
        return this;
    }

    public List<String> getPlotIds() {
        return this.plotIds;
    }

    public void setPlotIds(List<String> plotIds) {
        this.plotIds = plotIds;
    }

    public Season plotIds(List<String> plotIds) {
        setPlotIds(plotIds);
        return this;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Season notes(String notes) {
        setNotes(notes);
        return this;
    }

}
