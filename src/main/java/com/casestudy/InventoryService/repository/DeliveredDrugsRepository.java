//package com.casestudy.InventoryService.repository;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.casestudy.InventoryService.entity.DeliveredDrugs;
//
//@Repository
//public interface DeliveredDrugsRepository extends JpaRepository<DeliveredDrugs, Long> {
//	 List<DeliveredDrugs> findByDeliveryStatus(String deliveryStatus);
//
//	DeliveredDrugs findByBatchId(String batchId);
//}

package com.casestudy.InventoryService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.InventoryService.entity.DeliveredDrugs;

import java.util.List;

@Repository
public interface DeliveredDrugsRepository extends MongoRepository<DeliveredDrugs, String> {
    List<DeliveredDrugs> findByDeliveryStatus(String deliveryStatus);

    DeliveredDrugs findByBatchId(String batchId);
}

