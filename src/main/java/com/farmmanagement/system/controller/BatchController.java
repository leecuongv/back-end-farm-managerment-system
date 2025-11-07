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
import org.springframework.data.domain.Sort;
import java.util.List;

@Tag(name = "Batches", description = "Batch and lot management endpoints")
@RestController
@RequestMapping("/batches")
public class BatchController {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List batches", description = "List batches for a farm with optional filters and sorting")
    @GetMapping
    public List<Batch> getBatchesByFarm(
            @RequestParam String farmId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "entryDate") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (type != null) {
            return batchRepository.findByFarmIdAndType(farmId, type, sort);
        } else {
            return batchRepository.findByFarmId(farmId, sort);
        }
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
