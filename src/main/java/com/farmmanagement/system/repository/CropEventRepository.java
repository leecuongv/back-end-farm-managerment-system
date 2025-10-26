package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.CropEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CropEventRepository extends MongoRepository<CropEvent, String> {
    List<CropEvent> findByFarmId(String farmId);
    List<CropEvent> findByPlotId(String plotId);
}
