package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.FeedPlan;
import com.farmmanagement.system.repository.FeedPlanRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed-plans")
public class FeedPlanController {

    @Autowired
    private FeedPlanRepository feedPlanRepository;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public List<FeedPlan> getFeedPlansByFarm(@RequestParam String farmId) {
        return feedPlanRepository.findByFarmId(farmId);
    }

    @PostMapping
    public FeedPlan createFeedPlan(@RequestBody FeedPlan feedPlan) {
        String userId = SecurityUtils.getRequiredUserId();
        FeedPlan newFeedPlan = feedPlanRepository.save(feedPlan);
        auditService.logEvent(userId, "CREATE_FEED_PLAN", "FeedPlan", newFeedPlan.getId(), "Created new feed plan: " + newFeedPlan.getName());
        return newFeedPlan;
    }

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
