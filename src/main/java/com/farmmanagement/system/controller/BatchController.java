package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Batch;
import com.farmmanagement.system.repository.BatchRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Batches", description = "Batch and lot management endpoints")
@RestController
@RequestMapping("/batches")
public class BatchController {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List batches", description = "List batches for a farm")
    @GetMapping
    public List<Batch> getBatchesByFarm(@RequestParam String farmId) {
        return batchRepository.findByFarmId(farmId);
    }

    @Operation(summary = "Create batch", description = "Create a new batch/lot")
    @PostMapping
    public Batch createBatch(@RequestBody Batch batch) {
        String userId = SecurityUtils.getRequiredUserId();
        Batch newBatch = batchRepository.save(batch);
        auditService.logEvent(userId, "CREATE_BATCH", "Batch", newBatch.getId(), "Created new batch: " + newBatch.getBatchCode());
        return newBatch;
    }

    @Operation(summary = "Get batch", description = "Get batch details by id")
    @GetMapping("/{id}")
    public ResponseEntity<Batch> getBatchById(@PathVariable String id) {
        return batchRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
