package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.InventoryLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryLogRepository extends MongoRepository<InventoryLog, String> {
    List<InventoryLog> findByFarmId(String farmId);
    List<InventoryLog> findByFarmId(String farmId, Sort sort);
    List<InventoryLog> findByFarmIdAndType(String farmId, String type, Sort sort);
    List<InventoryLog> findByItemId(String itemId);
    List<InventoryLog> findByItemId(String itemId, Sort sort);
}
