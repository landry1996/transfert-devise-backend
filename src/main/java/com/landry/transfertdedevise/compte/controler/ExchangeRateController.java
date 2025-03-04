package com.landry.transfertdedevise.compte.controler;

import com.landry.transfertdedevise.compte.services.ExchangeRateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/Exchange-Rate/USD-CAD")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/{fromCurrency}/{toCurrency}")
    @PreAuthorize("hasAnyRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public BigDecimal getExchangeRate(@PathVariable String fromCurrency,
                                      @PathVariable String toCurrency) {
        return exchangeRateService.getExchangeRate(fromCurrency, toCurrency);
    }
}
