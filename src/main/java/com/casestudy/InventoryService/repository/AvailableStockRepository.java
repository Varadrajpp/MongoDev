

package com.casestudy.InventoryService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.InventoryService.entity.AvailableStock;

@Repository
public interface AvailableStockRepository extends MongoRepository<AvailableStock, String> {

    void deleteByDrugName(String drugName);

    void deleteByBatchId(String batchId);

    List<AvailableStock> findByDrugName(String drugName);

    AvailableStock findByBatchId(String batchId);

    List<AvailableStock> findAllByBatchId(String batchId);

    AvailableStock findBySupplierEmail(String supplierEmail);
}

