package com.sahil.ledger_module.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.ledger_module.service.ValidationService;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    @Autowired
    private ValidationService validationService;

    @PostMapping("/validate")
    public ResponseEntity<String> validateTransaction(@RequestBody Map<String, Double> entries) {
        
        validationService.validateTransaction(entries);
        return ResponseEntity.ok("Transaction is valid and balanced.");
    }
}