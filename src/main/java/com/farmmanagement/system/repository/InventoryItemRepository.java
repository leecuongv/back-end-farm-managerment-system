package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.InventoryItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryItemRepository extends MongoRepository<InventoryItem, String> {
    List<InventoryItem> findByFarmId(String farmId);
    List<InventoryItem> findByFarmId(String farmId, Sort sort);
    List<InventoryItem> findByFarmIdAndCategory(String farmId, String category, Sort sort);
}
