package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "animals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {
    @Id
    private String id;
    private String farmId;
    private String tagId;
    private String species;
    private AnimalType animalType;
    private String batchId;
    private String enclosureId;
    private String feedPlanId;
    private LocalDate birthDate;
    private AnimalStatus status;
    private List<HealthRecord> healthRecords;
    private List<GrowthRecord> growthRecords;
    private List<ReproductionLog> reproductionLogs;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Animal id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public Animal farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Animal tagId(String tagId) {
        setTagId(tagId);
        return this;
    }

    public String getSpecies() {
        return this.species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Animal species(String species) {
        setSpecies(species);
        return this;
    }

    public AnimalType getAnimalType() {
        return this.animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public Animal animalType(AnimalType animalType) {
        setAnimalType(animalType);
        return this;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Animal batchId(String batchId) {
        setBatchId(batchId);
        return this;
    }

    public String getEnclosureId() {
        return this.enclosureId;
    }

    public void setEnclosureId(String enclosureId) {
        this.enclosureId = enclosureId;
    }

    public Animal enclosureId(String enclosureId) {
        setEnclosureId(enclosureId);
        return this;
    }

    public String getFeedPlanId() {
        return this.feedPlanId;
    }

    public void setFeedPlanId(String feedPlanId) {
        this.feedPlanId = feedPlanId;
    }

    public Animal feedPlanId(String feedPlanId) {
        setFeedPlanId(feedPlanId);
        return this;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Animal birthDate(LocalDate birthDate) {
        setBirthDate(birthDate);
        return this;
    }

    public AnimalStatus getStatus() {
        return this.status;
    }

    public void setStatus(AnimalStatus status) {
        this.status = status;
    }

    public Animal status(AnimalStatus status) {
        setStatus(status);
        return this;
    }

    public List<HealthRecord> getHealthRecords() {
        return this.healthRecords;
    }

    public void setHealthRecords(List<HealthRecord> healthRecords) {
        this.healthRecords = healthRecords;
    }

    public Animal healthRecords(List<HealthRecord> healthRecords) {
        setHealthRecords(healthRecords);
        return this;
    }

    public List<GrowthRecord> getGrowthRecords() {
        return this.growthRecords;
    }

    public void setGrowthRecords(List<GrowthRecord> growthRecords) {
        this.growthRecords = growthRecords;
    }

    public Animal growthRecords(List<GrowthRecord> growthRecords) {
        setGrowthRecords(growthRecords);
        return this;
    }

    public List<ReproductionLog> getReproductionLogs() {
        return this.reproductionLogs;
    }

    public void setReproductionLogs(List<ReproductionLog> reproductionLogs) {
        this.reproductionLogs = reproductionLogs;
    }

    public Animal reproductionLogs(List<ReproductionLog> reproductionLogs) {
        setReproductionLogs(reproductionLogs);
        return this;
    }

    public enum AnimalType {
        BREEDING_FEMALE,
        DEVELOPMENT,
        FATTENING,
        YOUNG
    }

    public enum AnimalStatus {
        HEALTHY,
        SICK,
        SOLD,
        DEAD
    }
}
