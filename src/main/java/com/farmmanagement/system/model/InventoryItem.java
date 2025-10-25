package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventoryItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {
    @Id
    private String id;
    private String farmId;
    private String name;
    private String category; // e.g., FEED, MEDICINE, FERTILIZER, SEED
    private double quantity;
    private String unit;
    private double lowStockThreshold;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InventoryItem id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public InventoryItem farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InventoryItem name(String name) {
        setName(name);
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public InventoryItem category(String category) {
        setCategory(category);
        return this;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public InventoryItem quantity(double quantity) {
        setQuantity(quantity);
        return this;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public InventoryItem unit(String unit) {
        setUnit(unit);
        return this;
    }

    public double getLowStockThreshold() {
        return this.lowStockThreshold;
    }

    public void setLowStockThreshold(double lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public InventoryItem lowStockThreshold(double lowStockThreshold) {
        setLowStockThreshold(lowStockThreshold);
        return this;
    }

}
