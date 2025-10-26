package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.CropEvent;
import com.farmmanagement.system.repository.CropEventRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "CropEvents", description = "Cultivation logs and crop events")
@RestController
@RequestMapping("/crop-events")
public class CropEventController {

    @Autowired
    private CropEventRepository cropEventRepository;

    @Autowired
    private AuditService auditService;

    @GetMapping
    @Operation(summary = "List crop events", description = "List crop events; optional filter by farmId or plotId")
    public List<CropEvent> listEvents(@Parameter(description = "Filter by farm id") @RequestParam(required = false) String farmId,
                                      @Parameter(description = "Filter by plot id") @RequestParam(required = false) String plotId) {
        if (plotId != null) return cropEventRepository.findByPlotId(plotId);
        if (farmId != null) return cropEventRepository.findByFarmId(farmId);
        return cropEventRepository.findAll();
    }

    @Operation(summary = "Create crop event", description = "Record a new cultivation event (planting, irrigation, harvest, etc.)")
    @PostMapping
    public CropEvent createEvent(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Crop event payload") @RequestBody CropEvent event) {
        String userId = SecurityUtils.getRequiredUserId();
        if (event.getDate() == null) event.setDate(LocalDate.now());
        event.setRecordedBy(userId);
        CropEvent saved = cropEventRepository.save(event);
        auditService.logEvent(userId, "CREATE_CROP_EVENT", "CropEvent", saved.getId(), "Event: " + saved.getEventType());
        return saved;
    }

    @Operation(summary = "Get crop event", description = "Retrieve a crop event by id")
    @GetMapping("/{id}")
    public ResponseEntity<CropEvent> getEvent(@Parameter(description = "Crop event id") @PathVariable String id) {
        return cropEventRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete crop event", description = "Delete a crop event by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@Parameter(description = "Crop event id") @PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return cropEventRepository.findById(id).map(evt -> {
            cropEventRepository.delete(evt);
            auditService.logEvent(userId, "DELETE_CROP_EVENT", "CropEvent", id, "Deleted crop event: " + evt.getEventType());
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
