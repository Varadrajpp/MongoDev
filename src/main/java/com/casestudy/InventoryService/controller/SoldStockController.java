package com.casestudy.InventoryService.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.InventoryService.entity.LogEntry;
import com.casestudy.InventoryService.entity.SoldStock;
import com.casestudy.InventoryService.repository.SoldStockRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/inventory/sold-stock")
@CrossOrigin(origins = "http://localhost:4200")
public class SoldStockController {

    @Autowired
    private SoldStockRepository soldStockRepository;

    private static final Logger logger = LoggerFactory.getLogger(SoldStockController.class);

    @PostMapping("api/logs")
    public ResponseEntity<String> receiveLog(@RequestBody LogEntry logEntry) {
        // Log the received log entry and HTTP status code using the configured logger
        logger.info("HTTP Status Code: {} - {}", HttpStatus.OK.value(), logEntry.getMessage());

        return ResponseEntity.ok("Log received and stored successfully");
    }

    @PostMapping
    public ResponseEntity<List<SoldStock>> addSoldStock(@RequestBody List<SoldStock> soldStockList) {
        long startTime = System.nanoTime();
        try {
            for (SoldStock soldStock : soldStockList) {
                int quantity = soldStock.getQuantity();
                double price = soldStock.getPrice();
                double totalPrice = quantity * price;
                soldStock.setTotalPrice(totalPrice);
            }
            List<SoldStock> savedSoldStockList = soldStockRepository.saveAll(soldStockList);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Sold Stock Added Successfully", HttpStatus.CREATED.value(), responseTime);
            return new ResponseEntity<>(savedSoldStockList, HttpStatus.CREATED);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while adding sold stock: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<SoldStock>> getAllSoldStock() {
        long startTime = System.nanoTime();
        try {
            List<SoldStock> soldStockList = soldStockRepository.findAll();
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - All Sold Stock Fetched Successfully", HttpStatus.OK.value(), responseTime);
            return new ResponseEntity<>(soldStockList, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while fetching all sold stock: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoldStock> getSoldStockById(@PathVariable Long id) {
        long startTime = System.nanoTime();
        try {
            SoldStock soldStock = soldStockRepository.findById(id).orElse(null);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Sold Stock By ID Fetched Successfully", HttpStatus.OK.value(), responseTime);
            return new ResponseEntity<>(soldStock, HttpStatus.OK);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while fetching sold stock by ID: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoldStockById(@PathVariable Long id) {
        long startTime = System.nanoTime();
        try {
            soldStockRepository.deleteById(id);
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Sold Stock Deleted Successfully", HttpStatus.NO_CONTENT.value(), responseTime);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while deleting sold stock by ID: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateSalesReport() throws IOException, Exception {
        long startTime = System.nanoTime();
        List<SoldStock> soldStockList = soldStockRepository.findAll();

        // Create a new document
        Document document = new Document();

        try {
            // Create a ByteArrayOutputStream to hold the generated PDF content
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Create a PdfWriter instance to write the document to the output stream
            PdfWriter.getInstance(document, outputStream);

            // Open the document
            document.open();

            // Add a title to the document
            document.add(new Paragraph("                                                                   Sales Report                         "));
            document.add(new Paragraph("                                                                  "));
            // Add sold stock details to the document
            for (SoldStock soldStock : soldStockList) {
                document.add(new Paragraph("Batch ID: " + soldStock.getBatchId()));
                document.add(new Paragraph("Drug Name: " + soldStock.getDrugName()));
                document.add(new Paragraph("Supplier Email: " + soldStock.getSupplierEmail()));
                document.add(new Paragraph("Quantity: " + soldStock.getQuantity()));
                document.add(new Paragraph("Expiry Date: " + soldStock.getExpiryDate()));
                document.add(new Paragraph("Price: " + soldStock.getPrice()));
                document.add(new Paragraph("Total Price: " + soldStock.getTotalPrice()));
                document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            }

            // Close the document
            document.close();

            // Set the appropriate headers for the HTTP response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "sales_report.pdf");
            long responseTime = System.nanoTime() - startTime;
            logger.info("HTTP Status Code: {}, ResponseTime: {} ns - Sold Stock Report Downloaded", HttpStatus.OK.value(), responseTime);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (DocumentException e) {
            long responseTime = System.nanoTime() - startTime;
            logger.error("HTTP Status Code: {}, ResponseTime: {} ns - Error occurred while generating sales report: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), responseTime, e.getMessage());
            throw e;
        }
    }
}
