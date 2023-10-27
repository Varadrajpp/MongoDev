//package com.casestudy.InventoryService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AvailableStockControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testAddAvailableStock() throws Exception {
//        String requestBody = "[{\"batchId\":\"BATCH123\",\"drugName\":\"Drug 1\",\"supplierEmail\":\"supplier@example.com\",\"quantity\":10,\"expiryDate\":\"2023-12-31\",\"price\":9.99}]";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/inventory/available-stock")
//                .content(requestBody)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @Test
//    public void testGetAllAvailableStock() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/inventory/available-stock")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @Test
//    public void testGetAvailableStockById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/inventory/available-stock/{id}", 1)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @Test
//    public void testDeleteAvailableStockById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/inventory/available-stock/{id}", 1)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//}
