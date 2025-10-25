package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.AnimalEvent;
import com.farmmanagement.system.repository.AnimalEventRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/animal-events")
public class AnimalEventController {

    @Autowired
    private AnimalEventRepository animalEventRepository;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public List<AnimalEvent> getEventsByAnimal(@RequestParam String animalId) {
        return animalEventRepository.findByAnimalId(animalId);
    }

    @PostMapping
    public AnimalEvent createAnimalEvent(@RequestBody AnimalEvent event) {
        String userId = SecurityUtils.getRequiredUserId();
        AnimalEvent newEvent = animalEventRepository.save(event);
        auditService.logEvent(userId, "CREATE_ANIMAL_EVENT", "AnimalEvent", newEvent.getId(),
                "Created event " + newEvent.getType() + " for animal " + newEvent.getAnimalId());
        return newEvent;
    }
}
