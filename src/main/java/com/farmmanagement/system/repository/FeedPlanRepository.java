package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.FeedPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FeedPlanRepository extends MongoRepository<FeedPlan, String> {
    List<FeedPlan> findByFarmId(String farmId);
}
