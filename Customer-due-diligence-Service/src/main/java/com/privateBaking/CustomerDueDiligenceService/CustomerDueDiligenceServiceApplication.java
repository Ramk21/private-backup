package com.privateBaking.CustomerDueDiligenceService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient	
//@EnableFeignClients("com.privateBaking.CustomerDueDiligenceService")
public class CustomerDueDiligenceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerDueDiligenceServiceApplication.class, args);
	}

}
