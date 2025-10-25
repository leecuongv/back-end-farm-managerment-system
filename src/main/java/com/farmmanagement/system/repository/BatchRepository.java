package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BatchRepository extends MongoRepository<Batch, String> {
    List<Batch> findByFarmId(String farmId);
}
