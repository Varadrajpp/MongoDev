package com.casestudy.InventoryService.entity;

public class SupplierDTO {
    private String supplierEmail;
    private String drugName;

    // Constructor
    public SupplierDTO(String supplierEmail, String drugName) {
        this.supplierEmail = supplierEmail;
        this.drugName = drugName;
    }

    // Getters and Setters
    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
}
