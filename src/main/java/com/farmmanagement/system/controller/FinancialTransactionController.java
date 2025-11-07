package com.farmmanagement.system.controller;

import com.farmmanagement.system.model.FinancialTransaction;
import com.farmmanagement.system.repository.FinancialTransactionRepository;
import com.farmmanagement.system.security.SecurityUtils;
import com.farmmanagement.system.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Financial Transactions", description = "Financial transaction endpoints")
@RestController
@RequestMapping("/financial-transactions")
public class FinancialTransactionController {

    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Autowired
    private AuditService auditService;

    @Operation(summary = "List transactions", description = "List financial transactions for a farm with optional filters and sorting")
    @GetMapping
    public List<FinancialTransaction> getTransactionsByFarm(
            @RequestParam String farmId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false, defaultValue = "date") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (type != null && startDate != null && endDate != null) {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            return financialTransactionRepository.findByFarmIdAndTypeAndDateBetween(farmId, type, start, end, sort);
        } else if (startDate != null && endDate != null) {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            return financialTransactionRepository.findByFarmIdAndDateBetween(farmId, start, end, sort);
        } else if (type != null) {
            return financialTransactionRepository.findByFarmIdAndType(farmId, type, sort);
        } else {
            return financialTransactionRepository.findByFarmId(farmId, sort);
        }
    }

    @Operation(summary = "Create transaction", description = "Create a new financial transaction")
    @PostMapping
    public FinancialTransaction createTransaction(@RequestBody FinancialTransaction transaction) {
        String userId = SecurityUtils.getRequiredUserId();
        FinancialTransaction newTransaction = financialTransactionRepository.save(transaction);
        auditService.logEvent(userId, "CREATE_FINANCIAL_TRANSACTION", "FinancialTransaction", newTransaction.getId(),
                "Logged transaction of " + newTransaction.getAmount() + " for " + newTransaction.getDescription());
        return newTransaction;
    }
}
