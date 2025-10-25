package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.InventoryAudit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryAuditRepository extends MongoRepository<InventoryAudit, String> {
    List<InventoryAudit> findByFarmId(String farmId);
}
