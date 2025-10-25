package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.InventoryItem;
import com.farmmanagement.system.repository.InventoryItemRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inventory Items", description = "Inventory catalog endpoints")
@RestController
@RequestMapping("/inventory-items")
public class InventoryItemController {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List inventory items", description = "Retrieve inventory items for a given farm.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Items retrieved",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = InventoryItem[].class),
                examples = @ExampleObject(
                    name = "InventoryItemList",
                    value = "[{\"id\":\"64b1d2f3e5a6c7d8e9f0a1e5\",\"farmId\":\"farm-123\",\"name\":\"Starter Feed\",\"category\":\"FEED\",\"quantity\":150.5,\"unit\":\"kg\"}]"
                )
            )
        )
    })
    @GetMapping
    public List<InventoryItem> getItemsByFarm(@RequestParam String farmId) {
        return inventoryItemRepository.findByFarmId(farmId);
    }

    @Operation(summary = "Create inventory item", description = "Add a new inventory item to the catalog.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Item created",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = InventoryItem.class),
                examples = @ExampleObject(
                    name = "CreatedInventoryItem",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1e5\",\"farmId\":\"farm-123\",\"name\":\"Starter Feed\",\"category\":\"FEED\",\"quantity\":150.5,\"unit\":\"kg\",\"lowStockThreshold\":50}"
                )
            )
        )
    })
    @PostMapping
    public InventoryItem createInventoryItem(@RequestBody InventoryItem item) {
        String userId = SecurityUtils.getRequiredUserId();
        InventoryItem newItem = inventoryItemRepository.save(item);
        auditService.logEvent(userId, "CREATE_INVENTORY_ITEM", "InventoryItem", newItem.getId(), "Created inventory item: " + newItem.getName());
        return newItem;
    }

    @Operation(summary = "Update inventory item", description = "Update an existing inventory item.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Item updated",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = InventoryItem.class),
                examples = @ExampleObject(
                    name = "UpdatedInventoryItem",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1e5\",\"farmId\":\"farm-123\",\"name\":\"Starter Feed\",\"category\":\"FEED\",\"quantity\":140.0,\"unit\":\"kg\",\"lowStockThreshold\":50}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateInventoryItem(@PathVariable String id, @RequestBody InventoryItem itemDetails) {
        String userId = SecurityUtils.getRequiredUserId();
        return inventoryItemRepository.findById(id)
                .map(item -> {
                    item.setName(itemDetails.getName());
                    item.setCategory(itemDetails.getCategory());
                    item.setQuantity(itemDetails.getQuantity());
                    item.setUnit(itemDetails.getUnit());
                    item.setLowStockThreshold(itemDetails.getLowStockThreshold());
                    InventoryItem updatedItem = inventoryItemRepository.save(item);
                    auditService.logEvent(userId, "UPDATE_INVENTORY_ITEM", "InventoryItem", updatedItem.getId(), "Updated inventory item: " + updatedItem.getName());
                    return ResponseEntity.ok(updatedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete inventory item", description = "Remove an item from the inventory catalog.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Item removed",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "DeleteAcknowledgement", value = "{}"))
        ),
        @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventoryItem(@PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return inventoryItemRepository.findById(id)
                .map(item -> {
                    inventoryItemRepository.delete(item);
                    auditService.logEvent(userId, "DELETE_INVENTORY_ITEM", "InventoryItem", id, "Deleted inventory item: " + item.getName());
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
