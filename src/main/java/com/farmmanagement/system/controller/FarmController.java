package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Farm;
import com.farmmanagement.system.repository.FarmRepository;
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

@Tag(name = "Farms", description = "Farm management endpoints")
@RestController
@RequestMapping("/farms")
public class FarmController {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List farms", description = "Retrieve all farms in the system.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Farms retrieved",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Farm[].class),
                examples = @ExampleObject(
                    name = "FarmList",
                    value = "[{\"id\":\"64b1d2f3e5a6c7d8e9f0a1b2\",\"name\":\"North Ridge Farm\",\"location\":\"Colorado\"}]"
                )
            )
        )
    })
    @GetMapping
    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    @Operation(summary = "Create farm", description = "Create a new farm record.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Farm created",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Farm.class),
                examples = @ExampleObject(
                    name = "CreatedFarm",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1b2\",\"name\":\"North Ridge Farm\",\"location\":\"Colorado\"}"
                )
            )
        )
    })
    @PostMapping
    public Farm createFarm(@RequestBody Farm farm) {
        String userId = SecurityUtils.getRequiredUserId();
        Farm newFarm = farmRepository.save(farm);
        auditService.logEvent(userId, "CREATE_FARM", "Farm", newFarm.getId(), "Created new farm: " + newFarm.getName());
        return newFarm;
    }

    @Operation(summary = "Get farm", description = "Fetch farm details by identifier.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Farm found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Farm.class),
                examples = @ExampleObject(
                    name = "Farm",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1b2\",\"name\":\"North Ridge Farm\",\"location\":\"Colorado\"}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Farm not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Farm> getFarmById(@PathVariable String id) {
        return farmRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update farm", description = "Update farm name and location.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Farm updated",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Farm.class),
                examples = @ExampleObject(
                    name = "UpdatedFarm",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1b2\",\"name\":\"North Ridge Farm\",\"location\":\"Utah\"}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Farm not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Farm> updateFarm(@PathVariable String id, @RequestBody Farm farmDetails) {
        String userId = SecurityUtils.getRequiredUserId();
        return farmRepository.findById(id)
                .map(farm -> {
                    farm.setName(farmDetails.getName());
                    farm.setLocation(farmDetails.getLocation());
                    Farm updatedFarm = farmRepository.save(farm);
                    auditService.logEvent(userId, "UPDATE_FARM", "Farm", updatedFarm.getId(), "Updated farm details for: " + updatedFarm.getName());
                    return ResponseEntity.ok(updatedFarm);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete farm", description = "Remove a farm from the system.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Farm removed",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(name = "DeleteAcknowledgement", value = "{}")
            )
        ),
        @ApiResponse(responseCode = "404", description = "Farm not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarm(@PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return farmRepository.findById(id)
                .map(farm -> {
                    farmRepository.delete(farm);
                    auditService.logEvent(userId, "DELETE_FARM", "Farm", id, "Deleted farm: " + farm.getName());
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
