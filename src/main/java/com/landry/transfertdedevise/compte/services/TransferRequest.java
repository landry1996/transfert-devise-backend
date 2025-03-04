package com.landry.transfertdedevise.compte.services;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransferRequest {

    private Long accountSourceId;
    private Long accountDestinationId;
    private BigDecimal amount;
}
