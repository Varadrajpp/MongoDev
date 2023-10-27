package com.casestudy.InventoryService.repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orders-service", url = "http://localhost:8082") 
public interface CartMicroserviceClient {

	 @DeleteMapping("/cart/removeItemsByBatchId/{batchId}")
	    public void removeItemsByBatchId(@PathVariable String batchId);
}
