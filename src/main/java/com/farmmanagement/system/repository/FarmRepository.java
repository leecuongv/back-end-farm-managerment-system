package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Farm;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FarmRepository extends MongoRepository<Farm, String> {
}
