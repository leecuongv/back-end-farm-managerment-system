package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "animalEvents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalEvent {
    @Id
    private String id;
    private String farmId;
    private String animalId;
    private AnimalEventType type;
    private LocalDate date;
    private String notes;
    private double price;
    private String recordedBy;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AnimalEvent id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public AnimalEvent farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getAnimalId() {
        return this.animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public AnimalEvent animalId(String animalId) {
        setAnimalId(animalId);
        return this;
    }

    public AnimalEventType getType() {
        return this.type;
    }

    public void setType(AnimalEventType type) {
        this.type = type;
    }

    public AnimalEvent type(AnimalEventType type) {
        setType(type);
        return this;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AnimalEvent date(LocalDate date) {
        setDate(date);
        return this;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public AnimalEvent notes(String notes) {
        setNotes(notes);
        return this;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AnimalEvent price(double price) {
        setPrice(price);
        return this;
    }

    public String getRecordedBy() {
        return this.recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public AnimalEvent recordedBy(String recordedBy) {
        setRecordedBy(recordedBy);
        return this;
    }
    public enum AnimalEventType {
        ENTRY,
        SALE,
        DEATH,
        SELECT_BREEDER
    }
}
