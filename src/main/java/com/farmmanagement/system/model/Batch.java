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

@Document(collection = "batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Batch {
    @Id
    @Schema(description = "Unique identifier for the batch", example = "batch123")
    private String id;
    @Schema(description = "Unique identifier for the farm", example = "farm123")
    private String farmId;
    @Schema(description = "Unique code for the batch", example = "BATCH-001")
    private String batchCode;
    @Schema(description = "Type of the batch", example = "ANIMAL")
    private BatchType type;
    @Schema(description = "Description of the batch", example = "Batch for animal feed")
    private String description;
    @Schema(description = "Source of the batch", example = "Local Supplier")
    private String source;
    @Schema(description = "Entry date of the batch", example = "2024-01-15")
    private LocalDate entryDate;
    @Schema(description = "List of related item IDs", example = "[\"item123\", \"item456\"]")
    private List<String> relatedItemIds;

    public enum BatchType {
        ANIMAL, CROP, INVENTORY
    }
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Batch id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public Batch farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getBatchCode() {
        return this.batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Batch batchCode(String batchCode) {
        setBatchCode(batchCode);
        return this;
    }

    public BatchType getType() {
        return this.type;
    }

    public void setType(BatchType type) {
        this.type = type;
    }

    public Batch type(BatchType type) {
        setType(type);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Batch description(String description) {
        setDescription(description);
        return this;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Batch source(String source) {
        setSource(source);
        return this;
    }

    public LocalDate getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Batch entryDate(LocalDate entryDate) {
        setEntryDate(entryDate);
        return this;
    }

    public List<String> getRelatedItemIds() {
        return this.relatedItemIds;
    }

    public void setRelatedItemIds(List<String> relatedItemIds) {
        this.relatedItemIds = relatedItemIds;
    }

    public Batch relatedItemIds(List<String> relatedItemIds) {
        setRelatedItemIds(relatedItemIds);
        return this;
    }
}