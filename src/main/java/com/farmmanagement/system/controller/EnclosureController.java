package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Enclosure;
import com.farmmanagement.system.repository.EnclosureRepository;
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

@Tag(name = "Enclosures", description = "Enclosure management endpoints")
@RestController
@RequestMapping("/enclosures")
public class EnclosureController {

    @Autowired
    private EnclosureRepository enclosureRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List enclosures", description = "Retrieve enclosures for a specified farm.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Enclosures retrieved",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Enclosure[].class),
                examples = @ExampleObject(
                    name = "EnclosureList",
                    value = "[{\"id\":\"64b1d2f3e5a6c7d8e9f0a1d4\",\"farmId\":\"farm-123\",\"name\":\"Breeding Barn\",\"type\":\"BREEDING_PEN\",\"capacity\":20}]"
                )
            )
        )
    })
    @GetMapping
    public List<Enclosure> getEnclosuresByFarm(@RequestParam String farmId) {
        return enclosureRepository.findByFarmId(farmId);
    }

    @Operation(summary = "Create enclosure", description = "Create a new enclosure entry.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Enclosure created",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Enclosure.class),
                examples = @ExampleObject(
                    name = "CreatedEnclosure",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1d4\",\"farmId\":\"farm-123\",\"name\":\"Breeding Barn\",\"type\":\"BREEDING_PEN\",\"capacity\":20}"
                )
            )
        )
    })
    @PostMapping
    public Enclosure createEnclosure(@RequestBody Enclosure enclosure) {
        String userId = SecurityUtils.getRequiredUserId();
        Enclosure newEnclosure = enclosureRepository.save(enclosure);
        auditService.logEvent(userId, "CREATE_ENCLOSURE", "Enclosure", newEnclosure.getId(), "Created new enclosure: " + newEnclosure.getName());
        return newEnclosure;
    }

    @Operation(summary = "Update enclosure", description = "Update enclosure details by identifier.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Enclosure updated",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Enclosure.class),
                examples = @ExampleObject(
                    name = "UpdatedEnclosure",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1d4\",\"farmId\":\"farm-123\",\"name\":\"Nursery Barn\",\"type\":\"YOUNG_PEN\",\"capacity\":18}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Enclosure not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Enclosure> updateEnclosure(@PathVariable String id, @RequestBody Enclosure enclosureDetails) {
        String userId = SecurityUtils.getRequiredUserId();
        return enclosureRepository.findById(id)
                .map(enclosure -> {
                    enclosure.setName(enclosureDetails.getName());
                    enclosure.setType(enclosureDetails.getType());
                    enclosure.setCapacity(enclosureDetails.getCapacity());
                    Enclosure updatedEnclosure = enclosureRepository.save(enclosure);
                    auditService.logEvent(userId, "UPDATE_ENCLOSURE", "Enclosure", updatedEnclosure.getId(), "Updated enclosure: " + updatedEnclosure.getName());
                    return ResponseEntity.ok(updatedEnclosure);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete enclosure", description = "Remove an enclosure entry.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Enclosure removed",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "DeleteAcknowledgement", value = "{}"))
        ),
        @ApiResponse(responseCode = "404", description = "Enclosure not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnclosure(@PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return enclosureRepository.findById(id)
                .map(enclosure -> {
                    enclosureRepository.delete(enclosure);
                    auditService.logEvent(userId, "DELETE_ENCLOSURE", "Enclosure", id, "Deleted enclosure: " + enclosure.getName());
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
