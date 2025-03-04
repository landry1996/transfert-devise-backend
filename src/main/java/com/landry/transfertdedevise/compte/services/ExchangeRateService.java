package com.landry.transfertdedevise.compte.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {

    private static final BigDecimal TAUX_CHANGE_USD_CAD = BigDecimal.valueOf(1.25);

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        if ("USD".equals(fromCurrency) && "CAD".equals(toCurrency)) {
            return TAUX_CHANGE_USD_CAD;
        } else if ("CAD".equals(fromCurrency) && "USD".equals(toCurrency)) {
            return BigDecimal.ONE.divide(TAUX_CHANGE_USD_CAD, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            throw new RuntimeException("Unsupported conversion");
        }
    }
}
