package com.casestudy.InventoryService.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.InventoryService.entity.SoldStock;

@Repository
public interface SoldStockRepository extends JpaRepository<SoldStock, Long> {
}
