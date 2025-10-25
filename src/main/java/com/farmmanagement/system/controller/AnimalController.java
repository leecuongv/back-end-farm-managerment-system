package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Animal;
import com.farmmanagement.system.repository.AnimalRepository;
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

@Tag(name = "Animals", description = "Livestock management endpoints")
@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List animals", description = "Retrieve animals for a given farm.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Animals retrieved",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Animal[].class),
                examples = @ExampleObject(
                    name = "AnimalList",
                    value = "[{\"id\":\"64b1d2f3e5a6c7d8e9f0a1c3\",\"farmId\":\"farm-123\",\"tagId\":\"PIG-001\",\"species\":\"Pig\",\"status\":\"HEALTHY\"}]"
                )
            )
        )
    })
    @GetMapping
    public List<Animal> getAnimalsByFarm(@RequestParam String farmId) {
        return animalRepository.findByFarmId(farmId);
    }

    @Operation(summary = "Create animal", description = "Register a new animal record.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Animal created",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Animal.class),
                examples = @ExampleObject(
                    name = "CreatedAnimal",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1c3\",\"farmId\":\"farm-123\",\"tagId\":\"PIG-002\",\"species\":\"Pig\",\"status\":\"HEALTHY\"}"
                )
            )
        )
    })
    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal) {
        String userId = SecurityUtils.getRequiredUserId();
        Animal newAnimal = animalRepository.save(animal);
        auditService.logEvent(userId, "CREATE_ANIMAL", "Animal", newAnimal.getId(), "Created new animal: " + newAnimal.getTagId());
        return newAnimal;
    }

    @Operation(summary = "Get animal", description = "Fetch animal details by identifier.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Animal found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Animal.class),
                examples = @ExampleObject(
                    name = "Animal",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1c3\",\"farmId\":\"farm-123\",\"tagId\":\"PIG-001\",\"species\":\"Pig\",\"status\":\"HEALTHY\"}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Animal not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable String id) {
        return animalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update animal", description = "Update the details of an existing animal.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Animal updated",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Animal.class),
                examples = @ExampleObject(
                    name = "UpdatedAnimal",
                    value = "{\"id\":\"64b1d2f3e5a6c7d8e9f0a1c3\",\"farmId\":\"farm-123\",\"tagId\":\"PIG-001\",\"status\":\"SICK\"}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Animal not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable String id, @RequestBody Animal animalDetails) {
        String userId = SecurityUtils.getRequiredUserId();
        return animalRepository.findById(id)
                .map(animal -> {
                    // Update relevant fields
                    animal.setTagId(animalDetails.getTagId());
                    animal.setStatus(animalDetails.getStatus());
                    animal.setEnclosureId(animalDetails.getEnclosureId());
                    animal.setFeedPlanId(animalDetails.getFeedPlanId());
                    // Add more fields as needed
                    Animal updatedAnimal = animalRepository.save(animal);
                    auditService.logEvent(userId, "UPDATE_ANIMAL", "Animal", updatedAnimal.getId(), "Updated animal: " + updatedAnimal.getTagId());
                    return ResponseEntity.ok(updatedAnimal);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete animal", description = "Remove an animal record.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Animal removed",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "DeleteAcknowledgement", value = "{}"))
        ),
        @ApiResponse(responseCode = "404", description = "Animal not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return animalRepository.findById(id)
                .map(animal -> {
                    animalRepository.delete(animal);
                    auditService.logEvent(userId, "DELETE_ANIMAL", "Animal", id, "Deleted animal: " + animal.getTagId());
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
