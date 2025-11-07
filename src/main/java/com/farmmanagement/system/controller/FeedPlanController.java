package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.FeedPlan;
import com.farmmanagement.system.repository.FeedPlanRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import java.util.List;

@Tag(name = "Feed Plans", description = "Feed plan management endpoints")
@RestController
@RequestMapping("/feed-plans")
public class FeedPlanController {

    @Autowired
    private FeedPlanRepository feedPlanRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List feed plans", description = "List feed plans for a farm with optional filters and sorting")
    @GetMapping
    public List<FeedPlan> getFeedPlansByFarm(
            @RequestParam String farmId,
            @RequestParam(required = false) String stage,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        if (stage != null) {
            return feedPlanRepository.findByFarmIdAndStage(farmId, stage, sort);
        } else {
            return feedPlanRepository.findByFarmId(farmId, sort);
        }
    }

    @Operation(summary = "Create feed plan", description = "Create a new feed plan")
    @PostMapping
    public FeedPlan createFeedPlan(@RequestBody FeedPlan feedPlan) {
        String userId = SecurityUtils.getRequiredUserId();
        FeedPlan newFeedPlan = feedPlanRepository.save(feedPlan);
        auditService.logEvent(userId, "CREATE_FEED_PLAN", "FeedPlan", newFeedPlan.getId(), "Created new feed plan: " + newFeedPlan.getName());
        return newFeedPlan;
    }

    @Operation(summary = "Update feed plan", description = "Update a feed plan")
    @PutMapping("/{id}")
    public ResponseEntity<FeedPlan> updateFeedPlan(@PathVariable String id, @RequestBody FeedPlan feedPlanDetails) {
        String userId = SecurityUtils.getRequiredUserId();
        return feedPlanRepository.findById(id)
                .map(feedPlan -> {
                    feedPlan.setName(feedPlanDetails.getName());
                    feedPlan.setDescription(feedPlanDetails.getDescription());
                    feedPlan.setStage(feedPlanDetails.getStage());
                    feedPlan.setFeedDetails(feedPlanDetails.getFeedDetails());
                    FeedPlan updatedFeedPlan = feedPlanRepository.save(feedPlan);
                    auditService.logEvent(userId, "UPDATE_FEED_PLAN", "FeedPlan", updatedFeedPlan.getId(), "Updated feed plan: " + updatedFeedPlan.getName());
                    return ResponseEntity.ok(updatedFeedPlan);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete feed plan", description = "Delete a feed plan by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedPlan(@PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return feedPlanRepository.findById(id)
                .map(feedPlan -> {
                    feedPlanRepository.delete(feedPlan);
                    auditService.logEvent(userId, "DELETE_FEED_PLAN", "FeedPlan", id, "Deleted feed plan: " + feedPlan.getName());
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
