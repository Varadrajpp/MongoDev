package com.casestudy.InventoryService.config;

public class OpsgenieAlertPayload {
    private String message;

    public OpsgenieAlertPayload(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
