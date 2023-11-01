package com.casestudy.InventoryService.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sold_stock")
public class SoldStock {
    @Id
    private String id;
    private String batchId;
    private String drugName;
    private String supplierEmail;
    private int quantity;
    private String expiryDate;
    private double price;
    private double totalPrice;

    public SoldStock() {
        // Default constructor
    }

    public SoldStock(String batchId, String drugName, String supplierEmail, int quantity, String expiryDate, double price, double totalPrice) {
        this.batchId = batchId;
        this.drugName = drugName;
        this.supplierEmail = supplierEmail;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    // Getters and setters for all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
