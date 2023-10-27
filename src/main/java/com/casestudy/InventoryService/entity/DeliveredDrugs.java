package com.casestudy.InventoryService.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivered_drugs")
public class DeliveredDrugs {
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
    
    @Column(name = "delivery_status")
    private String deliveryStatus;
    
    @Column(name="doctor_name")
    private String doctorName;
    
    

	public DeliveredDrugs() {
		super();
	}

	public DeliveredDrugs(Long id, String batchId, String drugName, String supplierEmail, int quantity,
			String expiryDate, double price, String deliveryStatus, String doctorName) {
		super();
		this.id = id;
		this.batchId = batchId;
		this.drugName = drugName;
		this.supplierEmail = supplierEmail;
		this.quantity = quantity;
		this.expiryDate = expiryDate;
		this.price = price;
		this.deliveryStatus = deliveryStatus;
		this.doctorName = doctorName;
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

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	
	
}
