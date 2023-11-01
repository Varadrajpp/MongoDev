package com.casestudy.InventoryService.controller;

import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.InventoryService.entity.AvailableStock;
import com.casestudy.InventoryService.entity.LogEntry;
import com.casestudy.InventoryService.entity.SoldStock;
import com.casestudy.InventoryService.exception.BatchNotFoundException;
import com.casestudy.InventoryService.exception.DrugNotFoundException;
import com.casestudy.InventoryService.exception.StockNotFoundException;
import com.casestudy.InventoryService.exception.UnableToAddStockException;
import com.casestudy.InventoryService.exception.UnableToDeleteStockException;
import com.casestudy.InventoryService.exception.UnableToUpdateStockException;
import com.casestudy.InventoryService.repository.AvailableStockRepository;
import com.casestudy.InventoryService.repository.SoldStockRepository;



@RestController
@RequestMapping("/inventory/available-stock")
@CrossOrigin(origins = "http://localhost:4200")
public class AvailableStockController {

    private static final Logger logger = LoggerFactory.getLogger(AvailableStockController.class);

//    private final OpsgenieAlertService opsgenieAlertService;
//
//    @Autowired
//    public AvailableStockController(OpsgenieAlertService opsgenieAlertService) {
//        this.opsgenieAlertService = opsgenieAlertService;
//    }
    
    @Autowired
    private AvailableStockRepository availableStockRepository;

    @Autowired
    private SoldStockRepository soldStockRepository;

    @PostMapping("api/logs")
    public ResponseEntity<String> receiveLog(@RequestBody LogEntry logEntry) {
        // Log the received log entry and HTTP status code using the configured logger
        logger.info("HTTP Status Code: {} - {}", HttpStatus.OK.value(), logEntry.getMessage());

        return ResponseEntity.ok("Log received and stored successfully");
    }

    @PostMapping
    public ResponseEntity<List<AvailableStock>> addAvailableStock(@RequestBody List<AvailableStock> availableStockList) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            List<AvailableStock> savedStocks = availableStockRepository.saveAll(availableStockList);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Course Updated", HttpStatus.OK.value(), responseTime,requestId);  
            return new ResponseEntity<>(savedStocks, HttpStatus.CREATED);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while adding available stock: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw new UnableToAddStockException("Unable to add stock: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AvailableStock>> getAllAvailableStock() throws Exception {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            List<AvailableStock> stocks = availableStockRepository.findAll();
            long responseTime = System.nanoTime() - startTime;
          
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Fetched All Available Stocks Successfully", HttpStatus.OK.value(), responseTime, requestId);
            sendOpsgenieAlert("HI OPSGENIE", requestId);

            return new ResponseEntity<>(stocks, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while retrieving available stock: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            sendOpsgenieAlert(e.getMessage(), requestId);

            throw e;
        }
    }
    
    private void sendOpsgenieAlert(String errorMessage, UUID requestId) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost request = new HttpPost("https://varadraj.app.opsgenie.com/v2/alerts");
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "e37b063d-f837-44a4-b98d-ab168c346b2b"); // Replace YOUR_API_KEY with your actual Opsgenie API key

            // Create a JSON payload for the alert
            String alertPayload = "{"
                + "\"message\": \"" + errorMessage + "\","
                + "\"description\": \"Error occurred while retrieving available stock\","
                + "\"entity\": \"" + requestId.toString() + "\""
                + "}";

            StringEntity entity = new StringEntity(alertPayload);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            // Handle the response if needed
            if (response.getStatusLine().getStatusCode() == 202) {
                logger.info("Alert sent to Opsgenie successfully.");
            } else {
                logger.error("Failed to send alert to Opsgenie. Status Code: " + response.getStatusLine().getStatusCode());
            }

            EntityUtils.consume(response.getEntity());
        } catch (Exception ex) {
            logger.error("Error sending alert to Opsgenie: " + ex.getMessage());
        }
    }
    
    //opsgenieAlertService.createAlert();
    @GetMapping("/{id}")
    public ResponseEntity<AvailableStock> getAvailableStockById(@PathVariable Long id) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            AvailableStock stock = availableStockRepository.findById(id.toString())
                    .orElseThrow(() -> new StockNotFoundException("Stock not found with ID: " + id));
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Stock Found with the Searched ID", HttpStatus.OK.value(), responseTime, requestId);
            return new ResponseEntity<>(stock, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while retrieving available stock by ID: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw new StockNotFoundException("Error occurred while retrieving available stock by ID");
        }
    }

    @GetMapping("/searchbyBatchid/{batchid}")
    public ResponseEntity<List<AvailableStock>> getAvailableStockByBatchId(@PathVariable String batchid) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            AvailableStock stock = availableStockRepository.findByBatchId(batchid);
            if (stock == null) {
                throw new BatchNotFoundException("Batch not found with ID: " + batchid);
            }
            List<AvailableStock> stocks = availableStockRepository.findAllByBatchId(batchid);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Search By Batch ID Successful", HttpStatus.OK.value(), responseTime, requestId);
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while retrieving available stock by Batch ID: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw new BatchNotFoundException("Error occurred while retrieving available stock by Batch ID");
        }
    }

    @DeleteMapping("/{did}")
    public ResponseEntity<Void> deleteAvailableStockById(@PathVariable Long did) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            availableStockRepository.deleteById(did.toString());
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Delete By StockID Successful", HttpStatus.NO_CONTENT.value(), responseTime, requestId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while deleting available stock by ID: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw new UnableToDeleteStockException("Unable to delete stock with ID: " + did);
        }
    }

    @DeleteMapping("/delete-by-drug-name/{drugName}")
    @Transactional
    public ResponseEntity<Void> deleteAvailableStockByDrugName(@PathVariable String drugName) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            availableStockRepository.deleteByDrugName(drugName);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Delete By DrugName Successful", HttpStatus.NO_CONTENT.value(), responseTime, requestId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while deleting available stock by drug name: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw new DrugNotFoundException("Unable to delete stock for drug: " + drugName);
        }
    }

    @DeleteMapping("/delete-by-batch-id/{batchId}")
    @Transactional
    public ResponseEntity<Void> deleteAvailableStockByBatchId(@PathVariable String batchId) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            availableStockRepository.deleteByBatchId(batchId);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Deleted Stock By Batch ID Successful", HttpStatus.NO_CONTENT.value(), responseTime, requestId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while deleting available stock by batch ID: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/search-by-drug-name/{drugName}")
    public ResponseEntity<List<AvailableStock>> searchAvailableStockByDrugName(@PathVariable String drugName) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            List<AvailableStock> stocks = availableStockRepository.findByDrugName(drugName);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Search By Drug Name Successful", HttpStatus.OK.value(), responseTime, requestId);
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while searching available stock by drug name: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AvailableStock> updateAvailableStock(@PathVariable Long id,
                                                              @RequestBody AvailableStock updatedStock) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        try {
            AvailableStock stock = availableStockRepository.findById(id.toString()).orElse(null);
            if (stock != null) {
                stock.setBatchId(updatedStock.getBatchId());
                stock.setDrugName(updatedStock.getDrugName());
                stock.setSupplierEmail(updatedStock.getSupplierEmail());
                stock.setQuantity(updatedStock.getQuantity());
                stock.setExpiryDate(updatedStock.getExpiryDate());
                stock.setPrice(updatedStock.getPrice());
                long responseTime = System.nanoTime() - startTime;
                logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Updated Stock Successfully", HttpStatus.OK.value(), responseTime, requestId);
                AvailableStock updated = availableStockRepository.save(stock);
                return new ResponseEntity<>(updated, HttpStatus.OK);
            }
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Unable to update stock with ID: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, id);
            throw new UnableToUpdateStockException("Unable to update stock with ID: " + id);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while updating available stock: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw new UnableToUpdateStockException("Error occurred while updating available stock");
        }
    }

    @DeleteMapping("/delete-by-batchid/{batchId}")
    @Transactional
    public ResponseEntity<Void> deleteAvailableStockandUpdate(@PathVariable String batchId) {
        long startTime = System.nanoTime();
        UUID requestId = UUID.randomUUID();
        AvailableStock availableStock = availableStockRepository.findByBatchId(batchId);
        if (availableStock != null) {
            SoldStock soldStock = new SoldStock();
            soldStock.setBatchId(availableStock.getBatchId());
            soldStock.setDrugName(availableStock.getDrugName());
            soldStock.setSupplierEmail(availableStock.getSupplierEmail());
            soldStock.setQuantity(availableStock.getQuantity());
            soldStock.setExpiryDate(availableStock.getExpiryDate());
            soldStock.setPrice(availableStock.getPrice());
            soldStock.setTotalPrice(availableStock.getQuantity() * availableStock.getPrice());

            soldStockRepository.save(soldStock);

            availableStockRepository.delete(availableStock);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Deleted Stock By Batch ID and Updated Successfully", HttpStatus.NO_CONTENT.value(), responseTime, requestId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        long responseTime = System.nanoTime() - startTime;
        logger.info("HTTP Status Code: {}, ResponseTime: {} ns, RequestId: {} - Batch ID Not Found", HttpStatus.NOT_FOUND.value(), responseTime, requestId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}