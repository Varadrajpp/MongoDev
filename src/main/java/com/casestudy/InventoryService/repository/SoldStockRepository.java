package com.casestudy.InventoryService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.InventoryService.entity.SoldStock;

@Repository
public interface SoldStockRepository extends MongoRepository<SoldStock, String> {
}
