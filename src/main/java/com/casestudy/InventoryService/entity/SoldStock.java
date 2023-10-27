package com.casestudy.InventoryService.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sold_stock")
public class SoldStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_id")
    private String batchId;

    @Column(name = "drug_name")
    private String drugName;

    @Column(name = "supplier_email")
    private String supplierEmail;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "price")
    private double price;
    
    @Column(name = "total_price")
    private double totalPrice;

	public SoldStock(Long id, String batchId, String drugName, String supplierEmail, int quantity, String expiryDate,
			double price, double totalPrice) {
		super();
		this.id = id;
		this.batchId = batchId;
		this.drugName = drugName;
		this.supplierEmail = supplierEmail;
		this.quantity = quantity;
		this.expiryDate = expiryDate;
		this.price = price;
		this.totalPrice = totalPrice;
	}

	public SoldStock() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
