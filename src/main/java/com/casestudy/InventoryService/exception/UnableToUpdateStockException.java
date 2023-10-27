package com.casestudy.InventoryService.exception;
public class UnableToUpdateStockException extends RuntimeException {
    public UnableToUpdateStockException(String message) {
        super(message);
    }
}
