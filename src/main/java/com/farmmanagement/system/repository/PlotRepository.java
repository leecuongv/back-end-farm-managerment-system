package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Plot;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlotRepository extends MongoRepository<Plot, String> {
    List<Plot> findByFarmId(String farmId);
    List<Plot> findByFarmId(String farmId, Sort sort);
}
