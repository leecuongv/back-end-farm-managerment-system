package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Season;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SeasonRepository extends MongoRepository<Season, String> {
    List<Season> findByFarmId(String farmId);
}
