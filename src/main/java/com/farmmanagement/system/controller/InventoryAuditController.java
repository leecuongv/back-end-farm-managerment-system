package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.InventoryAudit;
import com.farmmanagement.system.repository.InventoryAuditRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-audits")
public class InventoryAuditController {

    @Autowired
    private InventoryAuditRepository inventoryAuditRepository;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public List<InventoryAudit> getAuditsByFarm(@RequestParam String farmId) {
        return inventoryAuditRepository.findByFarmId(farmId);
    }

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
