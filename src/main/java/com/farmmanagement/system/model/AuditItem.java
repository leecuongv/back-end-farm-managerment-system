package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditItem {
    private String itemId;
    private String itemName;
    private double systemQuantity;
    private double actualQuantity;
    private double discrepancy;
    private String notes;
}
