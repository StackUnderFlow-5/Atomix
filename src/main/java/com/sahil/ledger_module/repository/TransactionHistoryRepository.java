package com.sahil.ledger_module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.ledger_module.model.TransactionHistory;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findByAccountId(Long accountId);
}