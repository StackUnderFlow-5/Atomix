package com.sahil.ledger_module.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sahil.ledger_module.exception.InconsistentDataException;
import com.sahil.ledger_module.repository.AccountRepository;

@Service
public class ValidationService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void validateTransaction(Map<String, Double> entries) {
     
        double totalSum = entries.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        if (totalSum != 0) {
            throw new InconsistentDataException("Imbalance: Sum is " + totalSum);
        }

        for (Map.Entry<String, Double> entry : entries.entrySet()) {
            String name = entry.getKey();
            Double amount = entry.getValue();

            accountRepository.findByAccountName(name).ifPresent(account -> {
                if (account.getBalance() + amount < 0) {
                    throw new InconsistentDataException("Security Alert: Account [" + name + "] cannot have a negative balance!");
                }
            });
        }
    }
}