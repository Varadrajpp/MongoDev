package com.casestudy.InventoryService.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.InventoryService.entity.AvailableStock;
import com.casestudy.InventoryService.entity.LogEntry;
import com.casestudy.InventoryService.entity.SupplierDTO;
import com.casestudy.InventoryService.repository.AvailableStockRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierController {

    @Autowired
    private AvailableStockRepository availableStockRepository;

    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @PostMapping("api/logs")
    public ResponseEntity<String> receiveLog(@RequestBody LogEntry logEntry) {
        // Log the received log entry and HTTP status code using the configured logger
        logger.info("HTTP Status Code: {} - {}", HttpStatus.OK.value(), logEntry.getMessage());

        return ResponseEntity.ok("Log received and stored successfully");
    }

    @PutMapping("/edit-supplier/{supplierEmail}")
    public ResponseEntity<Void> editSupplier(@PathVariable String supplierEmail, @RequestBody SupplierDTO updatedSupplierDTO) {
        long startTime = System.nanoTime();
        try {
            AvailableStock existingSupplier = availableStockRepository.findBySupplierEmail(supplierEmail);

            if (existingSupplier != null) {
                existingSupplier.setSupplierEmail(updatedSupplierDTO.getSupplierEmail());
                existingSupplier.setDrugName(updatedSupplierDTO.getDrugName());
                availableStockRepository.save(existingSupplier);
                long responseTime = System.nanoTime() - startTime;
                logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Supplier INFO Changed", HttpStatus.OK.value(), responseTime);
                return ResponseEntity.ok().build();
            } else {
                long responseTime = System.nanoTime() - startTime;
                logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Unable to Change the Supplier INFO", HttpStatus.NOT_FOUND.value(), responseTime);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while editing supplier: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/delete-supplier")
    @Transactional
    public ResponseEntity<Void> deleteSupplier(@RequestParam String drugName) {
        long startTime = System.nanoTime();
        try {
            availableStockRepository.deleteByDrugName(drugName);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Supplier Deleted Successfully", HttpStatus.NO_CONTENT.value(), responseTime);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while deleting supplier: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/view-suppliers")
    public ResponseEntity<List<SupplierDTO>> viewSuppliers() {
        long startTime = System.nanoTime();
        try {
            List<AvailableStock> availableStockList = availableStockRepository.findAll();
            List<SupplierDTO> supplierList = new ArrayList<>();

            for (AvailableStock stock : availableStockList) {
                SupplierDTO supplierDTO = new SupplierDTO(stock.getSupplierEmail(), stock.getDrugName());
                supplierList.add(supplierDTO);
            }
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - All Suppliers Fetched Successfully", HttpStatus.OK.value(), responseTime);
            return new ResponseEntity<>(supplierList, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while viewing suppliers: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }
}
