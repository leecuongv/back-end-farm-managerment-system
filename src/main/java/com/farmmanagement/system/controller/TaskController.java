package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Task;
import com.farmmanagement.system.repository.TaskRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import java.util.List;

@Tag(name = "Tasks", description = "Task and todo endpoints")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List tasks", description = "List tasks for a farm with optional filters and sorting")
    @GetMapping
    public List<Task> getTasksByFarm(
            @RequestParam String farmId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false, defaultValue = "dueDate") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (status != null) {
            return taskRepository.findByFarmIdAndStatus(farmId, status, sort);
        } else {
            return taskRepository.findByFarmId(farmId, sort);
        }
    }

    @Operation(summary = "Create task", description = "Create a new task")
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        String userId = SecurityUtils.getRequiredUserId();
        Task newTask = taskRepository.save(task);
        auditService.logEvent(userId, "CREATE_TASK", "Task", newTask.getId(), "Created task: " + newTask.getTitle());
        return newTask;
    }

    @Operation(summary = "Update task", description = "Update an existing task")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task taskDetails) {
        String userId = SecurityUtils.getRequiredUserId();
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setStatus(taskDetails.getStatus());
                    task.setDueDate(taskDetails.getDueDate());
                    task.setAssignedTo(taskDetails.getAssignedTo());
                    Task updatedTask = taskRepository.save(task);
                    auditService.logEvent(userId, "UPDATE_TASK", "Task", updatedTask.getId(), "Updated task: " + updatedTask.getTitle());
                    return ResponseEntity.ok(updatedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete task", description = "Delete a task by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        String userId = SecurityUtils.getRequiredUserId();
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    auditService.logEvent(userId, "DELETE_TASK", "Task", id, "Deleted task: " + task.getTitle());
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
