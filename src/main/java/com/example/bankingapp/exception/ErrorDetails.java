package com.example.bankingapp.exception;


import java.io.Serializable;
import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message,String details,String errorCode) {

}
