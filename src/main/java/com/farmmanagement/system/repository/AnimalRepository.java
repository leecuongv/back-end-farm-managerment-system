package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Animal;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AnimalRepository extends MongoRepository<Animal, String> {
    List<Animal> findByFarmId(String farmId);
    List<Animal> findByFarmId(String farmId, Sort sort);
    List<Animal> findByFarmIdAndStatus(String farmId, String status, Sort sort);
    List<Animal> findByFarmIdAndSpecies(String farmId, String species, Sort sort);
    List<Animal> findByFarmIdAndStatusAndSpecies(String farmId, String status, String species, Sort sort);
}
