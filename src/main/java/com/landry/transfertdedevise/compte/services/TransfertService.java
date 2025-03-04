package com.landry.transfertdedevise.compte.services;

import com.landry.transfertdedevise.compte.entity.Account;
import com.landry.transfertdedevise.compte.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransfertService {

    private final AccountRepository accountRepository;
    private final ExchangeRateService exchangeRateService;

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Account source Not Found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Account destination Not Found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal convertedAmount = convertAmount(amount, fromAccount.getCurrency(), toAccount.getCurrency());

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(convertedAmount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
    private BigDecimal convertAmount(BigDecimal amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }
        if ("USD".equals(fromCurrency) && "CAD".equals(toCurrency)) {
            return amount.multiply(BigDecimal.valueOf(1.25));
        }
        if ("CAD".equals(fromCurrency) && "USD".equals(toCurrency)) {
            return amount.divide(BigDecimal.valueOf(1.25), 2, BigDecimal.ROUND_HALF_UP);
        }
        throw new RuntimeException("Conversion non supportée");
    }
}
