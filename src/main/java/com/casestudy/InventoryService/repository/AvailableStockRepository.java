package com.casestudy.InventoryService.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.InventoryService.entity.AvailableStock;

@Repository
public interface AvailableStockRepository extends JpaRepository<AvailableStock, Long> {

	void deleteByDrugName(String drugName);

	void deleteByBatchId(String batchId);

	List<AvailableStock> findByDrugName(String drugName);

	

	AvailableStock findByBatchId(String batchId);

	List<AvailableStock> findAllByBatchId(String batchid);

	AvailableStock findBySupplierEmail(String supplierEmail);

	
}
