package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.AnimalEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AnimalEventRepository extends MongoRepository<AnimalEvent, String> {
    List<AnimalEvent> findByFarmId(String farmId);
    List<AnimalEvent> findByAnimalId(String animalId);
}
