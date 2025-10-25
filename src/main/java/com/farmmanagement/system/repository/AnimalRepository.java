package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AnimalRepository extends MongoRepository<Animal, String> {
    List<Animal> findByFarmId(String farmId);
}
