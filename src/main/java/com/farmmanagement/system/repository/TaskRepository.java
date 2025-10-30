package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByFarmId(String farmId);
    List<Task> findByFarmId(String farmId, Sort sort);
    List<Task> findByFarmIdAndStatus(String farmId, String status, Sort sort);
    List<Task> findByAssignedTo(String userId);
    List<Task> findByAssignedTo(String userId, Sort sort);
    List<Task> findByAssignedToAndStatus(String userId, String status, Sort sort);
}
