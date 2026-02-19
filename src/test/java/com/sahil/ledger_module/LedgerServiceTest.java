package com.sahil.ledger_module;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import com.sahil.ledger_module.model.Account;
import com.sahil.ledger_module.repository.AccountRepository;
import com.sahil.ledger_module.repository.TransactionHistoryRepository;
import com.sahil.ledger_module.service.LedgerService;

@SpringBootTest
public class LedgerServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionHistoryRepository historyRepository;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private TransactionTemplate transactionTemplate; // This is the secret to fixing the error

    @BeforeEach
    void setUp() {
        // We use the template to ensure the setup happens in a transaction
        transactionTemplate.execute(status -> {
            historyRepository.deleteAll();
            accountRepository.deleteAll();
            accountRepository.save(new Account(null, "Alice", new BigDecimal("1000.00")));
            accountRepository.save(new Account(null, "Bob", new BigDecimal("500.00")));
            accountRepository.save(new Account(null, "OtherAccount", new BigDecimal("0.00")));
            return null;
        });
    }

    @Test
    void testSuccessfulTransfer() {
        ledgerService.transferMoney("Alice", "Bob", new BigDecimal("200.00"));

        transactionTemplate.execute(status -> {
            Account updatedAlice = accountRepository.findByAccountName("Alice").get();
            assertEquals(0, new BigDecimal("800.00").compareTo(updatedAlice.getBalance()));
            return null;
        });
    }

    @Test
    void testConcurrencyWithThreads() throws InterruptedException {
        String name = "Sahil_Stress";
        transactionTemplate.execute(status -> {
            accountRepository.save(new Account(null, name, new BigDecimal("1000.00")));
            return null;
        });

        int threadCount = 10;
        BigDecimal amountToWithdraw = new BigDecimal("10.00");
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            service.submit(() -> {
                try {
                    latch.await();
                    // LedgerService.transferMoney is already @Transactional, so it will handle the lock!
                    ledgerService.transferMoney(name, "OtherAccount", amountToWithdraw);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        latch.countDown();
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);

        transactionTemplate.execute(status -> {
            Account finalAccount = accountRepository.findByAccountName(name).get();
            assertEquals(0, new BigDecimal("900.00").compareTo(finalAccount.getBalance()), 
                "Locking should result in exactly 900.00");
            return null;
        });
    }
}