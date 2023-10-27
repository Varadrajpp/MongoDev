package com.casestudy.InventoryService.exception;
public class UnableToDeleteStockException extends RuntimeException {
    public UnableToDeleteStockException(String message) {
        super(message);
    }
}
