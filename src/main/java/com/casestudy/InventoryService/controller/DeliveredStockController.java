package com.casestudy.InventoryService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.InventoryService.entity.DeliveredDrugs;
import com.casestudy.InventoryService.entity.LogEntry;
import com.casestudy.InventoryService.repository.DeliveredDrugsRepository;

@RestController
@RequestMapping("/inventory/delivered-stock")
public class DeliveredStockController {

    @Autowired
    private DeliveredDrugsRepository deliveredDrugsRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeliveredStockController.class);

    @PostMapping("api/logs")
    public ResponseEntity<String> receiveLog(@RequestBody LogEntry logEntry) {
        // Log the received log entry and HTTP status code using the configured logger
        logger.info("HTTP Status Code: {} - {}", HttpStatus.OK.value(), logEntry.getMessage());

        return ResponseEntity.ok("Log received and stored successfully");
    }

    @PostMapping
    public ResponseEntity<List<DeliveredDrugs>> addDeliveredStock(@RequestBody List<DeliveredDrugs> deliveredDrugsList) {
        long startTime = System.nanoTime();
        try {
            List<DeliveredDrugs> savedDeliveredDrugsList = deliveredDrugsRepository.saveAll(deliveredDrugsList);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Delivered Stock Added Successfully", HttpStatus.CREATED.value(), responseTime);
            return new ResponseEntity<>(savedDeliveredDrugsList, HttpStatus.CREATED);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while adding delivered stock: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<DeliveredDrugs>> getAllDeliveredStock() {
        long startTime = System.nanoTime();
        try {
            List<DeliveredDrugs> deliveredStockList = deliveredDrugsRepository.findAll();
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - All Delivered Stock Fetched Successfully", HttpStatus.OK.value(), responseTime);
            return new ResponseEntity<>(deliveredStockList, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while fetching all delivered stock: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/delivered")
    public ResponseEntity<List<DeliveredDrugs>> getDeliveredDrugs() {
        long startTime = System.nanoTime();
        try {
            List<DeliveredDrugs> deliveredStockList = deliveredDrugsRepository.findByDeliveryStatus("Delivered");
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Delivered Stock Fetched Successfully", HttpStatus.OK.value(), responseTime);
            return new ResponseEntity<>(deliveredStockList, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while fetching delivered stock: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveredDrugs> getDeliveredStockById(@PathVariable Long id) {
        long startTime = System.nanoTime();
        try {
            DeliveredDrugs deliveredStock = deliveredDrugsRepository.findById(id).orElse(null);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Delivered Stock By ID Fetched Successfully", HttpStatus.OK.value(), responseTime);
            return new ResponseEntity<>(deliveredStock, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while fetching delivered stock by ID: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/{BATCHID}/mark-as-delivered")
    public ResponseEntity<?> markStockAsDelivered(@PathVariable("BATCHID") String batchId) {
        long startTime = System.nanoTime();
        try {
            DeliveredDrugs deliveredStock = deliveredDrugsRepository.findByBatchId(batchId);
            if (deliveredStock != null) {
                deliveredStock.setDeliveryStatus("Delivered");
                deliveredDrugsRepository.save(deliveredStock);
                long responseTime = System.nanoTime() - startTime;
                logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Stock Marked as Delivered Successfully", HttpStatus.OK.value(), responseTime);
                return ResponseEntity.ok().body("{\"message\": \"Stock marked as Delivered\"}");
            } else {
                long responseTime = System.nanoTime() - startTime;
                logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Stock Not Found", HttpStatus.NOT_FOUND.value(), responseTime);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Stock not found\"}");
            }
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while marking stock as delivered: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }
}
