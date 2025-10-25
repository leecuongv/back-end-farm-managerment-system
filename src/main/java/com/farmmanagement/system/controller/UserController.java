package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.Role;
import com.farmmanagement.system.model.User;
import com.farmmanagement.system.repository.UserRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        String adminId = SecurityUtils.getRequiredUserId();
        // In a real app, you'd hash the password here before saving
        User newUser = userRepository.save(user);
        auditService.logEvent(adminId, "CREATE_USER", "User", newUser.getId(), "Created new user: " + newUser.getEmail());
        return newUser;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/assign-farm")
    public ResponseEntity<User> assignFarmToUser(@PathVariable String id, @RequestBody Map<String, String> payload) {
        String adminId = SecurityUtils.getRequiredUserId();
        String farmId = payload.get("farmId");
        String role = payload.get("role");

        return userRepository.findById(id)
                .map(user -> {
                    user.getFarmIds().add(farmId);
                    user.setRole(Role.valueOf(role.toUpperCase()));
                    User updatedUser = userRepository.save(user);
                    auditService.logEvent(adminId, "ASSIGN_FARM", "User", updatedUser.getId(), "Assigned farm " + farmId + " with role " + role);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<User> toggleUserActivation(@PathVariable String id, @RequestBody Map<String, Boolean> payload) {
        String adminId = SecurityUtils.getRequiredUserId();
        boolean isActive = payload.get("isActive");
        return userRepository.findById(id)
                .map(user -> {
                    user.setActive(isActive);
                    User updatedUser = userRepository.save(user);
                    String action = isActive ? "ACTIVATE_USER" : "DEACTIVATE_USER";
                    auditService.logEvent(adminId, action, "User", updatedUser.getId(), "User " + user.getEmail() + " status set to " + isActive);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
