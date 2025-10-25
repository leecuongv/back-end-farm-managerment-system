package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.InventoryLog;
import com.farmmanagement.system.repository.InventoryLogRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inventory Logs", description = "Inventory movement endpoints")
@RestController
@RequestMapping("/inventory-logs")
public class InventoryLogController {

    @Autowired
    private InventoryLogRepository inventoryLogRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List inventory logs", description = "Retrieve inventory logs for a given farm.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Logs retrieved",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = InventoryLog[].class),
                examples = @ExampleObject(
                    name = "InventoryLogList",
                    value = "[{\"id\":\"64b1d2f3e5a6c7d8e9f0a1f6\",\"farmId\":\"farm-123\",\"itemId\":\"item-001\",\"type\":\"OUT\",\"quantity\":5.0,\"notes\":\"Fed sows\"}]"
                )
            )
        )
    })
    @GetMapping
    public List<InventoryLog> getLogsByFarm(@RequestParam String farmId) {
        return inventoryLogRepository.findByFarmId(farmId);
    }

    @Operation(summary = "Create inventory log", description = "Record an inventory in/out transaction.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Log created",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = InventoryLog.class),
                examples = @ExampleObject(
                    name = "CreatedInventoryLog",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1f6\",\"farmId\":\"farm-123\",\"itemId\":\"item-001\",\"type\":\"OUT\",\"quantity\":5.0,\"notes\":\"Fed sows\"}"
                )
            )
        )
    })
    @PostMapping
    public InventoryLog createInventoryLog(@RequestBody InventoryLog log) {
        String userId = SecurityUtils.getRequiredUserId();
        // In a real app, you'd also update the quantity in the corresponding InventoryItem
        InventoryLog newLog = inventoryLogRepository.save(log);
        auditService.logEvent(userId, "CREATE_INVENTORY_LOG", "InventoryLog", newLog.getId(),
                "Logged " + newLog.getType() + " of " + newLog.getQuantity() + " for item " + newLog.getItemId());
        return newLog;
    }
}
