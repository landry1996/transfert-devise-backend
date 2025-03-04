package com.landry.transfertdedevise.compte.exception.handleexception;

import com.landry.transfertdedevise.compte.exception.BaseException;
import com.landry.transfertdedevise.compte.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestionnaire d'exception pour les exceptions personnalisées qui héritent de BaseException.
     *
     * @param ex L'exception capturée.
     * @return Une réponse HTTP contenant un message d'erreur détaillé.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorMessage> handleBaseException(BaseException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .status(ex.getStatusCode())
                .httpStatus(HttpStatus.valueOf(ex.getStatusCode()))
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getStatusCode()))
                .body(errorMessage);
    }

    /**
     * Gestionnaire d'exception générique pour les erreurs inattendues.
     *
     * @param ex L'exception capturée.
     * @return Une réponse HTTP contenant un message d'erreur générique.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message("An internal error has occurred: " + ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorMessage);
    }
}
