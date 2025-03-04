package com.landry.transfertdedevise.compte.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorMessage(String message, int status, HttpStatus httpStatus, LocalDateTime timestamp) {

}

