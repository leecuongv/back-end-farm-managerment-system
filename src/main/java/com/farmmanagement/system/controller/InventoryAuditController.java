package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.InventoryAudit;
import com.farmmanagement.system.repository.InventoryAuditRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inventory Audits", description = "Inventory audit endpoints")
@RestController
@RequestMapping("/inventory-audits")
public class InventoryAuditController {

    @Autowired
    private InventoryAuditRepository inventoryAuditRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List inventory audits", description = "Retrieve inventory audits for a farm")
    @GetMapping
    public List<InventoryAudit> getAuditsByFarm(@RequestParam String farmId) {
        return inventoryAuditRepository.findByFarmId(farmId);
    }

    @Operation(summary = "Create inventory audit", description = "Create a new inventory audit record")
    @PostMapping
    public InventoryAudit createInventoryAudit(@RequestBody InventoryAudit audit) {
        String userId = SecurityUtils.getRequiredUserId();
        // In a real app, you might want to calculate discrepancies here
        InventoryAudit newAudit = inventoryAuditRepository.save(audit);
        auditService.logEvent(userId, "CREATE_INVENTORY_AUDIT", "InventoryAudit", newAudit.getId(),
                "Created new inventory audit on " + newAudit.getDate());
        return newAudit;
    }
}
