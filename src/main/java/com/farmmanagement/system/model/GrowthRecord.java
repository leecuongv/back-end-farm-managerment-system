package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrowthRecord {
    private LocalDate date;
    private double weight;
    private String recordedBy;

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public GrowthRecord date(LocalDate date) {
        setDate(date);
        return this;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public GrowthRecord weight(double weight) {
        setWeight(weight);
        return this;
    }

    public String getRecordedBy() {
        return this.recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public GrowthRecord recordedBy(String recordedBy) {
        setRecordedBy(recordedBy);
        return this;
    }

}
