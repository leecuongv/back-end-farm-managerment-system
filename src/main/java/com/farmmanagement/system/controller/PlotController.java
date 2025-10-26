package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Plot;
import com.farmmanagement.system.repository.PlotRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Plots", description = "Crop plot/field management endpoints")
@RestController
@RequestMapping("/plots")
public class PlotController {

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List plots", description = "List all plots or filter by farmId")
    @GetMapping
    public List<Plot> getPlots(@RequestParam(required = false) String farmId) {
        if (farmId != null) {
            return plotRepository.findByFarmId(farmId);
        }
        return plotRepository.findAll();
    }

    @Operation(summary = "Create plot", description = "Create a new plot/field")
    @PostMapping
    public Plot createPlot(@RequestBody Plot plot) {
        String userId = SecurityUtils.getRequiredUserId();
        plot.setCreatedAt(LocalDateTime.now());
        Plot saved = plotRepository.save(plot);
        auditService.logEvent(userId, "CREATE_PLOT", "Plot", saved.getId(), "Created plot: " + saved.getName());
        return saved;
    }

    @Operation(summary = "Get plot", description = "Get plot details by id")
    @GetMapping("/{id}")
    public ResponseEntity<Plot> getPlotById(@PathVariable String id) {
        return plotRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update plot", description = "Update plot fields")
    @PutMapping("/{id}")
    public ResponseEntity<Plot> updatePlot(@PathVariable String id, @RequestBody Plot details) {
        String userId = SecurityUtils.getRequiredUserId();
        return plotRepository.findById(id).map(plot -> {
            plot.setName(details.getName());
            plot.setArea(details.getArea());
            plot.setLocation(details.getLocation());
            Plot updated = plotRepository.save(plot);
            auditService.logEvent(userId, "UPDATE_PLOT", "Plot", updated.getId(), "Updated plot: " + updated.getName());
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete plot", description = "Delete a plot by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlot(@PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return plotRepository.findById(id).map(plot -> {
            plotRepository.delete(plot);
            auditService.logEvent(userId, "DELETE_PLOT", "Plot", id, "Deleted plot: " + plot.getName());
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
