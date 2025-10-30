package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.Enclosure;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EnclosureRepository extends MongoRepository<Enclosure, String> {
    List<Enclosure> findByFarmId(String farmId);
    List<Enclosure> findByFarmId(String farmId, Sort sort);
    List<Enclosure> findByFarmIdAndType(String farmId, String type, Sort sort);
}
