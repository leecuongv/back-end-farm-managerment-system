package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecord {
    private LocalDate date;
    private String eventType;
    private String notes;
    private String recordedBy;

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HealthRecord date(LocalDate date) {
        setDate(date);
        return this;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public HealthRecord eventType(String eventType) {
        setEventType(eventType);
        return this;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public HealthRecord notes(String notes) {
        setNotes(notes);
        return this;
    }

    public String getRecordedBy() {
        return this.recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public HealthRecord recordedBy(String recordedBy) {
        setRecordedBy(recordedBy);
        return this;
    }

}
