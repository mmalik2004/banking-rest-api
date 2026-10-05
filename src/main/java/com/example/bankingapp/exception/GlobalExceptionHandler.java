package com.example.bankingapp.exception;


import org.springframework.boot.webmvc.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handle speicfic exception-AccountException
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorDetails> handleAccountException(AccountException excep, WebRequest request) {
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),
                excep.getMessage(),
                request.getDescription(false),
                "ACCOUNT_NOT_FOUND"


        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    //Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception excep, WebRequest request) {
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),
                excep.getMessage(),
                request.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
