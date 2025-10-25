package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "inventoryAudits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryAudit {
    @Id
    private String id;
    private String farmId;
    private LocalDate date;
    private String conductedBy;
    private String status;
    private List<AuditItem> items;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InventoryAudit id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public InventoryAudit farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public InventoryAudit date(LocalDate date) {
        setDate(date);
        return this;
    }

    public String getConductedBy() {
        return this.conductedBy;
    }

    public void setConductedBy(String conductedBy) {
        this.conductedBy = conductedBy;
    }

    public InventoryAudit conductedBy(String conductedBy) {
        setConductedBy(conductedBy);
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InventoryAudit status(String status) {
        setStatus(status);
        return this;
    }

    public List<AuditItem> getItems() {
        return this.items;
    }

    public void setItems(List<AuditItem> items) {
        this.items = items;
    }

    public InventoryAudit items(List<AuditItem> items) {
        setItems(items);
        return this;
    }

}
