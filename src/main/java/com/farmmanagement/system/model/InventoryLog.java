package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "inventoryLogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryLog {
    @Id
    private String id;
    private String farmId;
    private String itemId;
    private String batchCode;
    private LogType type; // IN or OUT
    private double quantity;
    private String notes;
    private UsageTarget usageTarget;
    private String recordedBy;
    private LocalDateTime date;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InventoryLog id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public InventoryLog farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public InventoryLog itemId(String itemId) {
        setItemId(itemId);
        return this;
    }

    public String getBatchCode() {
        return this.batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public InventoryLog batchCode(String batchCode) {
        setBatchCode(batchCode);
        return this;
    }

    public LogType getType() {
        return this.type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public InventoryLog type(LogType type) {
        setType(type);
        return this;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public InventoryLog quantity(double quantity) {
        setQuantity(quantity);
        return this;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public InventoryLog notes(String notes) {
        setNotes(notes);
        return this;
    }

    public UsageTarget getUsageTarget() {
        return this.usageTarget;
    }

    public void setUsageTarget(UsageTarget usageTarget) {
        this.usageTarget = usageTarget;
    }

    public InventoryLog usageTarget(UsageTarget usageTarget) {
        setUsageTarget(usageTarget);
        return this;
    }

    public String getRecordedBy() {
        return this.recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public InventoryLog recordedBy(String recordedBy) {
        setRecordedBy(recordedBy);
        return this;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public InventoryLog date(LocalDateTime date) {
        setDate(date);
        return this;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsageTarget {
        private TargetType type; // ENCLOSURE or ANIMAL
        private String id;
        public TargetType getType() {
            return this.type;
        }
        public void setType(TargetType type) {
            this.type = type;
        }
        public UsageTarget type(TargetType type) {
            setType(type);
            return this;
        }
        public String getId() {
            return this.id;
        }
        public void setId(String id) {
            this.id = id;
        }
    }

    public enum LogType {
        IN,
        OUT
    }

    public enum TargetType {
        ENCLOSURE,
        ANIMAL
    }
}
