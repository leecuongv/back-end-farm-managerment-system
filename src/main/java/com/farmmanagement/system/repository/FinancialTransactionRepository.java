package com.farmmanagement.system.repository;

import com.farmmanagement.system.model.FinancialTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FinancialTransactionRepository extends MongoRepository<FinancialTransaction, String> {
    List<FinancialTransaction> findByFarmId(String farmId);
}
