package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Task;
import com.farmmanagement.system.repository.TaskRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public List<Task> getTasksByFarm(@RequestParam String farmId) {
        return taskRepository.findByFarmId(farmId);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        String userId = SecurityUtils.getRequiredUserId();
        Task newTask = taskRepository.save(task);
        auditService.logEvent(userId, "CREATE_TASK", "Task", newTask.getId(), "Created task: " + newTask.getTitle());
        return newTask;
    }

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
