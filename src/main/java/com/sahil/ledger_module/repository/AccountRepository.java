package com.sahil.ledger_module.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahil.ledger_module.model.Account;

import jakarta.persistence.LockModeType;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE) 
    @Query("SELECT a FROM Account a WHERE a.accountName = :accountName") 
    Optional<Account> findByAccountName(@Param("accountName") String accountName); 
}