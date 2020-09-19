package com.privateBaking.CustomerDueDiligenceService.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.privateBaking.CustomerDueDiligenceService.Model.CustomerConfiguration;
import com.privateBaking.CustomerDueDiligenceService.Model.CustomerDueDiligence;
import com.privateBaking.CustomerDueDiligenceService.Repository.CustomerDueDiligenceRepository;


@RestController
@EnableHystrix
public class CustomerDueDiligenceController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerDueDiligenceController.class);
	

	
	@Autowired
	CustomerDueDiligenceRepository customerDueDiligenceRepository;
	
	@Autowired
	CustomerConfiguration customerConfiguration;
	
	CustomerDueDiligence customerDueDiligence;
	

	@GetMapping("customer/{customerId}/getStatus")
	public CustomerDueDiligence getDiligenceStatus(@PathVariable  Long customerId) {
		CustomerDueDiligence diligence = new CustomerDueDiligence();

		logger.info("CustomerDueDiligence calling getDiligenceStatus method...");
		Optional<CustomerDueDiligence> diligenceOptional = customerDueDiligenceRepository.findById(customerId);
		 if(diligenceOptional.isPresent()) { 
		     diligence = diligenceOptional.get();		
			 diligence.setCustomerId(customerId);
			 diligence.setDiligenceStatus("default");
			 customerDueDiligenceRepository.saveAndFlush(diligence);
		
				return diligence;
		 }
		 else {
		
			 diligence.setCustomerId(customerId);
			 diligence.setDiligenceStatus("pending");
			 customerDueDiligenceRepository.saveAndFlush(diligence);
			
				return diligence;
		 }

	}
	
	
	@HystrixCommand(fallbackMethod="fallBackMethod" )
	  @GetMapping("customer/{customerId}/v1/getDiligenceDetails") 
	public CustomerDueDiligence getDiligenceDetails(@PathVariable Long customerId) {
		customerDueDiligence = new CustomerDueDiligence();
	  logger.info("CustomerDueDiligence calling version1 getDiligenceDetails  method...");
		Optional<CustomerDueDiligence> diligenceOptional = customerDueDiligenceRepository.findById(customerId);
		if(diligenceOptional.isPresent()) 
			 customerDueDiligence= diligenceOptional.get();
		return customerDueDiligence;	
		 
	  }
	
	@HystrixCommand(fallbackMethod="fallBackMethod" )
	  @GetMapping("customer/{customerId}/v2/getDiligenceDetails") 
	public CustomerDueDiligence getDiligenceDetailsV2(@PathVariable Long customerId) {
		customerDueDiligence = new CustomerDueDiligence();
	  logger.info("CustomerDueDiligence calling version1 getDiligenceDetails method...");
		Optional<CustomerDueDiligence> diligenceOptional = customerDueDiligenceRepository.findById(customerId);
		if(diligenceOptional.isPresent()) 
		{
			 customerDueDiligence= diligenceOptional.get();
			 customerDueDiligence.setDiligenceStatus("version2 Status - "+customerDueDiligence.getDiligenceStatus());
			
		}
		return customerDueDiligence;	
		 
	  }
	 
		
	
	public CustomerDueDiligence fallBackMethod(Long id) {	
		customerConfiguration.setDefaultCustomerId(id);
		return new CustomerDueDiligence
	(customerConfiguration.getDefaultCustomerId(),customerConfiguration.getDefaultDiligenceStatus()) ;
	}

}

