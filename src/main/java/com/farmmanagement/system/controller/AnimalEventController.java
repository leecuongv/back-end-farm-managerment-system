package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.AnimalEvent;
import com.farmmanagement.system.repository.AnimalEventRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Animal Events", description = "Animal event logging endpoints")
@RestController
@RequestMapping("/animal-events")
public class AnimalEventController {

    @Autowired
    private AnimalEventRepository animalEventRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List animal events", description = "List events for a specific animal with optional filters and sorting")
    @GetMapping
    public List<AnimalEvent> getEventsByAnimal(
            @RequestParam(required = false) String animalId,
            @RequestParam(required = false) String farmId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "date") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection) {
        
        org.springframework.data.domain.Sort sort = sortDirection.equalsIgnoreCase("desc") 
            ? org.springframework.data.domain.Sort.by(sortBy).descending()
            : org.springframework.data.domain.Sort.by(sortBy).ascending();
        
        if (farmId != null && type != null) {
            return animalEventRepository.findByFarmIdAndType(farmId, type, sort);
        } else if (farmId != null) {
            return animalEventRepository.findByFarmId(farmId, sort);
        } else if (animalId != null) {
            return animalEventRepository.findByAnimalId(animalId, sort);
        } else {
            return animalEventRepository.findAll(sort);
        }
    }

    @Operation(summary = "Create animal event", description = "Record an event for an animal (e.g., vaccination, illness)")
    @PostMapping
    public AnimalEvent createAnimalEvent(@RequestBody AnimalEvent event) {
        String userId = SecurityUtils.getRequiredUserId();
        AnimalEvent newEvent = animalEventRepository.save(event);
        auditService.logEvent(userId, "CREATE_ANIMAL_EVENT", "AnimalEvent", newEvent.getId(),
                "Created event " + newEvent.getType() + " for animal " + newEvent.getAnimalId());
        return newEvent;
    }
}
