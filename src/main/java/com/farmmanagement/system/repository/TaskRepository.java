package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByFarmId(String farmId);
    List<Task> findByAssignedTo(String userId);
}
