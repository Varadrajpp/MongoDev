//package com.casestudy.InventoryService.config;
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.springframework.stereotype.Service;
//
//import com.ifountain.opsgenie.client.OpsGenieClient;
//import com.ifountain.opsgenie.client.swagger.ApiException;
//import com.ifountain.opsgenie.client.swagger.api.AlertApi;
//import com.ifountain.opsgenie.client.swagger.model.CreateAlertRequest;
//import com.ifountain.opsgenie.client.swagger.model.Recipient;
//import com.ifountain.opsgenie.client.swagger.model.SuccessResponse;
//import com.ifountain.opsgenie.client.swagger.model.TeamRecipient;
//
//@Service
//public class OpsgenieAlertService {
// 
//
//    
//   
//
//
//   public void createAlert() throws ApiException  {
//	   
//	   OpsGenieClient client1 = new OpsGenieClient();
//	   AlertApi client = client1.alertV2();
//	   client.getApiClient().setApiKey("41e64689-4449-4c73-acf6-a2fbfaabc300");
//
//
//	   CreateAlertRequest request = new CreateAlertRequest();
//	   request.setMessage("AppServer1 is down!");
//	   request.setAlias("Tron");
//	   request.setDescription("CPU usage is over 87%");
//	   request.setTeams(Arrays.asList(new TeamRecipient().name("OperationTeam"), new TeamRecipient().name("NetworkTeam")));
//	   request.setVisibleTo(Collections.singletonList((Recipient) new TeamRecipient().name("NetworkTeam")));
//	   request.setActions(Arrays.asList("ping", "restart"));
//	   request.setTags(Arrays.asList("network", "operations"));
//	   request.setEntity("ApppServer1");
//	   request.setPriority(CreateAlertRequest.PriorityEnum.P2);
//	   request.setUser("user@opsgenie.com");
//	   request.setNote("Alert created");
//
//	   SuccessResponse response = client.createAlert(request);
//	   Float took = response.getTook();
//	   String requestId = response.getRequestId();
//	   String message = response.getResult();
//}
//    
//    
////    public void sendAlert(String message) {
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", "GenieKey " + opsgenieApiKey);
////
////        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(opsgenieAlertsApiUrl)
////            .queryParam("message", message);
////
////        HttpEntity<String> request = new HttpEntity<>(headers);
////
////       System.out.println("Hello1");
////        ResponseEntity<String> response = restTemplate.exchange(
////            builder.toUriString(),
////            HttpMethod.GET,
////            request,
////            String.class
////            
////        );
////        System.out.println("Hello2");
////
////
////        // You can handle the response here if needed.
////    }
//}
