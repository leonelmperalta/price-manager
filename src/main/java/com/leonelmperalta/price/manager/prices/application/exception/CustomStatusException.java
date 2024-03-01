package com.leonelmperalta.price.manager.prices.application.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomStatusException extends CustomException {

    public CustomStatusException() {
    }

    public abstract HttpStatus getStatus();
}