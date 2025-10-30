package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.AnimalEvent;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AnimalEventRepository extends MongoRepository<AnimalEvent, String> {
    List<AnimalEvent> findByFarmId(String farmId);
    List<AnimalEvent> findByFarmId(String farmId, Sort sort);
    List<AnimalEvent> findByFarmIdAndType(String farmId, String type, Sort sort);
    List<AnimalEvent> findByAnimalId(String animalId);
    List<AnimalEvent> findByAnimalId(String animalId, Sort sort);
}
