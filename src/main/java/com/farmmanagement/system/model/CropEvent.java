package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Document(collection = "cropEvents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropEvent {
    @Id
    @Schema(description = "Unique identifier for the crop event", example = "evt123")
    private String id;

    @Schema(description = "Farm id this event belongs to", example = "farm123")
    private String farmId;

    @Schema(description = "Plot id related to this event", example = "plot123")
    private String plotId;

    @Schema(description = "Season id if applicable", example = "season123")
    private String seasonId;

    @Schema(description = "Type of event", example = "PLANTING/IRRIGATION/HARVEST")
    private String eventType;

    @Schema(description = "Event date")
    private LocalDate date;

    @Schema(description = "Additional notes")
    private String notes;

    @Schema(description = "User who recorded this event")
    private String recordedBy;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CropEvent id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public CropEvent farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getPlotId() {
        return this.plotId;
    }

    public void setPlotId(String plotId) {
        this.plotId = plotId;
    }

    public CropEvent plotId(String plotId) {
        setPlotId(plotId);
        return this;
    }

    public String getSeasonId() {
        return this.seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public CropEvent seasonId(String seasonId) {
        setSeasonId(seasonId);
        return this;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public CropEvent eventType(String eventType) {
        setEventType(eventType);
        return this;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CropEvent date(LocalDate date) {
        setDate(date);
        return this;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CropEvent notes(String notes) {
        setNotes(notes);
        return this;
    }

    public String getRecordedBy() {
        return this.recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public CropEvent recordedBy(String recordedBy) {
        setRecordedBy(recordedBy);
        return this;
    }

}
