package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionLog {
    private LocalDateTime date;
    private String itemId; // Refers to an inventory item
    private double quantity;
    private String recordedBy; // Refers to a User ID
    private String notes;

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ConsumptionLog date(LocalDateTime date) {
        setDate(date);
        return this;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ConsumptionLog itemId(String itemId) {
        setItemId(itemId);
        return this;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public ConsumptionLog quantity(double quantity) {
        setQuantity(quantity);
        return this;
    }

    public String getRecordedBy() {
        return this.recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public ConsumptionLog recordedBy(String recordedBy) {
        setRecordedBy(recordedBy);
        return this;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ConsumptionLog notes(String notes) {
        setNotes(notes);
        return this;
    }

}
