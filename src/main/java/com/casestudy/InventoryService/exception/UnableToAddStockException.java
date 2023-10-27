package com.casestudy.InventoryService.exception;
public class UnableToAddStockException extends RuntimeException {
    public UnableToAddStockException(String message) {
        super(message);
    }
}
