package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Batch;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BatchRepository extends MongoRepository<Batch, String> {
    List<Batch> findByFarmId(String farmId);
    List<Batch> findByFarmId(String farmId, Sort sort);
    List<Batch> findByFarmIdAndType(String farmId, String type, Sort sort);
}
