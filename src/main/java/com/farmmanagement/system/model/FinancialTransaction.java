package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "financialTransactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialTransaction {
    @Id
    private String id;
    private String farmId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private String category;
    private String relatedBatchId;
    private LocalDate date;
    private String recordedBy;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FinancialTransaction id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public FinancialTransaction farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public TransactionType getType() {
        return this.type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public FinancialTransaction type(TransactionType type) {
        setType(type);
        return this;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public FinancialTransaction amount(BigDecimal amount) {
        setAmount(amount);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FinancialTransaction description(String description) {
        setDescription(description);
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public FinancialTransaction category(String category) {
        setCategory(category);
        return this;
    }

    public String getRelatedBatchId() {
        return this.relatedBatchId;
    }

    public void setRelatedBatchId(String relatedBatchId) {
        this.relatedBatchId = relatedBatchId;
    }

    public FinancialTransaction relatedBatchId(String relatedBatchId) {
        setRelatedBatchId(relatedBatchId);
        return this;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public FinancialTransaction date(LocalDate date) {
        setDate(date);
        return this;
    }

    public String getRecordedBy() {
        return this.recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public FinancialTransaction recordedBy(String recordedBy) {
        setRecordedBy(recordedBy);
        return this;
    }


    public enum TransactionType {
        EXPENSE, REVENUE
    }
}
