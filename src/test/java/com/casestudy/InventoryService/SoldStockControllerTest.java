//package com.casestudy.InventoryService;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import com.casestudy.InventoryService.controller.SoldStockController;
//import com.casestudy.InventoryService.entity.SoldStock;
//import com.casestudy.InventoryService.repository.SoldStockRepository;
//
//
//@WebMvcTest(SoldStockController.class)
//@AutoConfigureMockMvc
//class SoldStockControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//
//    @MockBean
//    private SoldStockRepository soldStockRepository;
//
//   
//
//    @Test
//    void getAllSoldStock_ShouldReturnAllSoldStocks() throws Exception {
//
//        List<SoldStock> soldStockList = new ArrayList<>();
//        soldStockList.add(new SoldStock(1L, "BATCH-001", "Drug A", "supplier1@example.com", 10, "2023-06-30", 10.0, 100.0));
//        soldStockList.add(new SoldStock(2L, "BATCH-002", "Drug B", "supplier2@example.com", 5, "2023-07-31", 20.0, 100.0));
//
//        when(soldStockRepository.findAll()).thenReturn(soldStockList);
//
//        ResultActions resultActions = mockMvc.perform(get("/inventory/sold-stock"));
//
//        resultActions.andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].batchId").value("BATCH-001"))
//                .andExpect(jsonPath("$[0].drugName").value("Drug A"))
//                .andExpect(jsonPath("$[0].supplierEmail").value("supplier1@example.com"))
//                .andExpect(jsonPath("$[0].quantity").value(10))
//                .andExpect(jsonPath("$[0].expiryDate").value("2023-06-30"))
//                .andExpect(jsonPath("$[0].price").value(10.0))
//                .andExpect(jsonPath("$[0].totalPrice").value(100.0))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].batchId").value("BATCH-002"))
//                .andExpect(jsonPath("$[1].drugName").value("Drug B"))
//                .andExpect(jsonPath("$[1].supplierEmail").value("supplier2@example.com"))
//                .andExpect(jsonPath("$[1].quantity").value(5))
//                .andExpect(jsonPath("$[1].expiryDate").value("2023-07-31"))
//                .andExpect(jsonPath("$[1].price").value(20.0))
//                .andExpect(jsonPath("$[1].totalPrice").value(100.0));
//    }
//
//    @Test
//    void getSoldStockById_ShouldReturnSoldStockWithGivenId() throws Exception {
//   
//        SoldStock soldStock = new SoldStock(1L, "BATC01", "Drug A", "supplier1@example.com", 10, "2023-06-30", 10.0, 100.0);
//
//        when(soldStockRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(soldStock));
//
//        ResultActions resultActions = mockMvc.perform(get("/inventory/sold-stock/1"));
//
//        resultActions.andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.batchId").value("BATCH-001"))
//                .andExpect(jsonPath("$.drugName").value("Drug A"))
//                .andExpect(jsonPath("$.supplierEmail").value("supplier1@example.com"))
//                .andExpect(jsonPath("$.quantity").value(10))
//                .andExpect(jsonPath("$.expiryDate").value("2023-06-30"))
//                .andExpect(jsonPath("$.price").value(10.0))
//                .andExpect(jsonPath("$.totalPrice").value(100.0));
//    }
//
//    @Test
//    void deleteSoldStockById_ShouldDeleteSoldStockWithGivenId() throws Exception {
//       
//        ResultActions resultActions = mockMvc.perform(delete("/inventory/sold-stock/1"));
//        resultActions.andExpect(status().isOk());
//        verify(soldStockRepository, times(1)).deleteById(1L);
//    }
//}
