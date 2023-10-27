//package com.casestudy.InventoryService.config;
//import java.net.http.HttpClient;
//import java.security.cert.X509Certificate;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.X509TrustManager;
//
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class RestTemplateConfig {
//
//	  @Bean
//	    public RestTemplate restTemplate() throws Exception {
//	        // Create a custom SSL context that trusts all certificates
//	        SSLContext sslContext = SSLContext.getInstance("TLS");
//	        sslContext.init(null, new X509TrustManager[] {
//	            new X509TrustManager() {
//	                public void checkClientTrusted(X509Certificate[] chain, String authType) {
//	                }
//
//	                public void checkServerTrusted(X509Certificate[] chain, String authType) {
//	                }
//
//	                public X509Certificate[] getAcceptedIssuers() {
//	                    return null;
//	                }
//	            }
//	        }, null);
//
//	        // Create an HttpClient with the custom SSL context
//	        CloseableHttpClient httpClient = HttpClients.custom()
//	            .setSslcontext(sslContext)
//	            .build();
//
//	        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//
//	        return new RestTemplate(factory);
//	    }
//}
