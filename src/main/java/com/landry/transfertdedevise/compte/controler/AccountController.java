package com.landry.transfertdedevise.compte.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landry.transfertdedevise.compte.dto.AccountDto;
import com.landry.transfertdedevise.compte.services.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compte")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<AccountDto> addAccount(@Valid @RequestBody AccountDto accountDto){
        try {
            // Convertir en JSON et logger
            String jsonData = objectMapper.writeValueAsString(accountDto);
            log.info("📩 Données reçues pour la création d'un compte: {}", jsonData);
        } catch (Exception e) {
            log.error("❌ Erreur lors de la conversion de AccountDto en JSON", e);
        }
        return ResponseEntity.ok(accountService.addAccount(accountDto));
    }
}
