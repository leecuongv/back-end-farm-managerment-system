package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "enclosures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enclosure {
    @Id
    private String id;
    private String farmId;
    private String name;
    private EnclosureType type;
    private int capacity;
    private int currentOccupancy;
    private List<ConsumptionLog> feedConsumptionLogs;
    private List<ConsumptionLog> medicineConsumptionLogs;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Enclosure id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public Enclosure farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enclosure name(String name) {
        setName(name);
        return this;
    }

    public EnclosureType getType() {
        return this.type;
    }

    public void setType(EnclosureType type) {
        this.type = type;
    }

    public Enclosure type(EnclosureType type) {
        setType(type);
        return this;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Enclosure capacity(int capacity) {
        setCapacity(capacity);
        return this;
    }

    public int getCurrentOccupancy() {
        return this.currentOccupancy;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public Enclosure currentOccupancy(int currentOccupancy) {
        setCurrentOccupancy(currentOccupancy);
        return this;
    }

    public List<ConsumptionLog> getFeedConsumptionLogs() {
        return this.feedConsumptionLogs;
    }

    public void setFeedConsumptionLogs(List<ConsumptionLog> feedConsumptionLogs) {
        this.feedConsumptionLogs = feedConsumptionLogs;
    }

    public Enclosure feedConsumptionLogs(List<ConsumptionLog> feedConsumptionLogs) {
        setFeedConsumptionLogs(feedConsumptionLogs);
        return this;
    }

    public List<ConsumptionLog> getMedicineConsumptionLogs() {
        return this.medicineConsumptionLogs;
    }

    public void setMedicineConsumptionLogs(List<ConsumptionLog> medicineConsumptionLogs) {
        this.medicineConsumptionLogs = medicineConsumptionLogs;
    }

    public Enclosure medicineConsumptionLogs(List<ConsumptionLog> medicineConsumptionLogs) {
        setMedicineConsumptionLogs(medicineConsumptionLogs);
        return this;
    }

    public enum EnclosureType {
        BREEDING_PEN,
        DEVELOPMENT_PEN,
        FATTENING_PEN,
        YOUNG_PEN
    }
}
