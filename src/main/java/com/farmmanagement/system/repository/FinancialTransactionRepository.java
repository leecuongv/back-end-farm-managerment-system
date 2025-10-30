package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.FinancialTransaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialTransactionRepository extends MongoRepository<FinancialTransaction, String> {
    List<FinancialTransaction> findByFarmId(String farmId);
    List<FinancialTransaction> findByFarmId(String farmId, Sort sort);
    List<FinancialTransaction> findByFarmIdAndType(String farmId, String type, Sort sort);
    List<FinancialTransaction> findByFarmIdAndDateBetween(String farmId, LocalDateTime startDate, LocalDateTime endDate, Sort sort);
    List<FinancialTransaction> findByFarmIdAndTypeAndDateBetween(String farmId, String type, LocalDateTime startDate, LocalDateTime endDate, Sort sort);
}
