package com.landry.transfertdedevise.compte.controler;

import com.landry.transfertdedevise.compte.services.TransferRequest;
import com.landry.transfertdedevise.compte.services.TransfertService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transfert")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class TransferController {

    private final TransfertService transfertService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public String effectuerTransfert(@RequestBody TransferRequest request) {
        transfertService.transfer(request.getAccountSourceId(), request.getAccountDestinationId(), request.getAmount());
        return "Transfer successful";
    }
}
