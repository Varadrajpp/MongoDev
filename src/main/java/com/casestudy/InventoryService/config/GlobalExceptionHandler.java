package com.casestudy.InventoryService.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e) {
        // Log the exception
        logger.error("An exception occurred: " + e.getMessage(), e);

        // You can customize the response entity based on your requirements
        return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
