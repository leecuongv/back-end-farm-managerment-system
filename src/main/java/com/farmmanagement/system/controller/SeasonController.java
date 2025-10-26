package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Season;
import com.farmmanagement.system.model.Batch;
import com.farmmanagement.system.repository.SeasonRepository;
import com.farmmanagement.system.repository.BatchRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Seasons", description = "Crop season management endpoints")
@RestController
@RequestMapping("/seasons")
public class SeasonController {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List seasons", description = "List all seasons or filter by farmId")
    @GetMapping
    public List<Season> getSeasons(@RequestParam(required = false) String farmId) {
        if (farmId != null) return seasonRepository.findByFarmId(farmId);
        return seasonRepository.findAll();
    }

    @Operation(summary = "Create season", description = "Create a new crop season")
    @PostMapping
    public Season createSeason(@RequestBody Season season) {
        String userId = SecurityUtils.getRequiredUserId();
        Season saved = seasonRepository.save(season);
        auditService.logEvent(userId, "CREATE_SEASON", "Season", saved.getId(), "Created season: " + saved.getName());
        return saved;
    }

    @Operation(summary = "Get season", description = "Get season details by id")
    @GetMapping("/{id}")
    public ResponseEntity<Season> getSeason(@PathVariable String id) {
        return seasonRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update season", description = "Update season details")
    @PutMapping("/{id}")
    public ResponseEntity<Season> updateSeason(@PathVariable String id, @RequestBody Season details) {
        String userId = SecurityUtils.getRequiredUserId();
        return seasonRepository.findById(id).map(season -> {
            season.setName(details.getName());
            season.setCropType(details.getCropType());
            season.setStartDate(details.getStartDate());
            season.setEndDate(details.getEndDate());
            season.setPlotIds(details.getPlotIds());
            season.setNotes(details.getNotes());
            Season updated = seasonRepository.save(season);
            auditService.logEvent(userId, "UPDATE_SEASON", "Season", updated.getId(), "Updated season: " + updated.getName());
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete season", description = "Delete a season by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeason(@PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return seasonRepository.findById(id).map(season -> {
            seasonRepository.delete(season);
            auditService.logEvent(userId, "DELETE_SEASON", "Season", id, "Deleted season: " + season.getName());
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a harvest batch linked to this season. Caller may provide batchCode and relatedItemIds.
     */
    @Operation(summary = "Create harvest batch", description = "Create a harvest batch linked to a season")
    @PostMapping("/{id}/harvests")
    public ResponseEntity<Batch> createHarvestBatch(@PathVariable String id, @RequestBody Batch batchPayload) {
        return seasonRepository.findById(id).map(season -> {
            String userId = SecurityUtils.getRequiredUserId();
            Batch batch = new Batch();
            batch.setFarmId(season.getFarmId());
            batch.setBatchCode(batchPayload.getBatchCode());
            batch.setType(Batch.BatchType.CROP);
            batch.setDescription(batchPayload.getDescription());
            batch.setEntryDate(batchPayload.getEntryDate());
            batch.setRelatedItemIds(batchPayload.getRelatedItemIds());
            Batch saved = batchRepository.save(batch);
            auditService.logEvent(userId, "CREATE_HARVEST_BATCH", "Batch", saved.getId(), "Created harvest batch for season: " + season.getName());
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }
}
