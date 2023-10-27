package com.casestudy.InventoryService.exception;
public class DrugNotFoundException extends RuntimeException {
    public DrugNotFoundException(String message) {
        super(message);
    }
}
