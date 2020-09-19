package com.privateBaking.CustomerProfileService.Controller;

import java.sql.Date;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.privateBaking.CustomerProfileService.Model.CustomerInfoDTO;
import com.privateBaking.CustomerProfileService.Model.CustomerProfile;
import com.privateBaking.CustomerProfileService.Model.CustomerStatus;
import com.privateBaking.CustomerProfileService.Model.DiligenceService;
import com.privateBaking.CustomerProfileService.Repository.CustomerProfileRepository;



@RestController
public class CustomerProfileController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerProfileController.class);
	
	@Autowired
	CustomerProfileRepository customerProfileRepository;
	
	
	CustomerProfile customerProfile;
	
	@Autowired
	DiligenceService diligenceService;
	
	private static final String DEFAULT_STATUS="default";
	private static final String PENDING_STATUS="pending";
	@PostMapping("customer/saveInfo")
	public void saveCustomerInfo(
			@RequestParam  String customerName, @RequestParam  String email, @RequestParam  Long mobileNo,	
			@RequestParam String userName, @RequestParam String password,
			@RequestParam String address, @RequestParam String state, @RequestParam String country,
			@RequestParam String panNo,@RequestParam Date dob,@RequestParam Long accountNo)
			 {
		logger.info("customer profile service post method..");
		customerProfile= new CustomerProfile();
		customerProfile.setName(customerName);
		customerProfile.setEmail(email);
		customerProfile.setMobileNo(mobileNo);
		customerProfile.setUserName(userName);
		customerProfile.setPasswrd(password);
		customerProfile.setAddress(address);
		customerProfile.setState(state);
		customerProfile.setCountry(country);
		customerProfile.setPanNo(panNo);
		customerProfile.setDob(dob);
		customerProfile.setAccountNo(accountNo);
		customerProfile.setCustomerStatus("NA");
		customerProfile.setDiligenceStatus(PENDING_STATUS);
		customerProfileRepository.saveAndFlush(customerProfile);	
		Optional<CustomerProfile> customerProfileEntity = customerProfileRepository.findById(customerProfile.getCustomerId());
		if(customerProfileEntity.isPresent()) {
			customerProfile= customerProfileEntity.get();
			}
		
		CustomerProfile customerProfile1=diligenceService.getDiligenceStatus(customerProfile.getCustomerId());
		
		if(customerProfile1.getDiligenceStatus().equals(DEFAULT_STATUS) ||
				 customerProfile1.getDiligenceStatus().equals(PENDING_STATUS)) {
			customerProfile.setCustomerStatus("A");
			customerProfile.setDiligenceStatus("standard");
			customerProfileRepository.saveAndFlush(customerProfile);
		}
		
	}
	
	@PutMapping("customer/{customerId}/updateCustomerInfo")
	public void UpdateCustomerInfo(@PathVariable  Long customerId,@RequestBody  CustomerInfoDTO customerInfoDTO) {
		customerProfile= new CustomerProfile();
		Optional<CustomerProfile> customerProfileEntity = customerProfileRepository.findById(customerId);
		if(customerProfileEntity.isPresent()) {
		customerProfile= customerProfileEntity.get();
		}
		Optional<CustomerInfoDTO> optionalCustomerInfoDTO = Optional.ofNullable(customerInfoDTO);
		if(optionalCustomerInfoDTO.isPresent()) {			
			Optional<String> optionalCustomerName= Optional.ofNullable(optionalCustomerInfoDTO.get().getName()).filter(name->!name.isEmpty());
			if(optionalCustomerName.isPresent()) {
			customerProfile.setName(optionalCustomerName.get());	
			}
		
			Optional<String> optionalEmail= Optional.ofNullable(optionalCustomerInfoDTO.get().getEmail()).filter(email->!email.isEmpty());
			if(optionalEmail.isPresent()) {
			customerProfile.setEmail(optionalEmail.get());
			}
		
		Optional<Long> optionalMobileNo= Optional.ofNullable(optionalCustomerInfoDTO.get().getMobileNo());
		if(optionalMobileNo.isPresent()) {
		customerProfile.setMobileNo(optionalMobileNo.get());
		}
		
		Optional<String> optionalUserName= Optional.ofNullable(optionalCustomerInfoDTO.get().getUserName()).filter(userName->!userName.isEmpty());
		if(optionalUserName.isPresent()) {
		customerProfile.setUserName(optionalUserName.get());
		}
		Optional<String> optionalPassword= Optional.ofNullable(optionalCustomerInfoDTO.get().getPasswrd()).filter(passwrd->!passwrd.isEmpty());
		if(optionalPassword.isPresent()) {
		customerProfile.setPasswrd(optionalPassword.get());
		}
		Optional<String> optionalAddress= Optional.ofNullable(optionalCustomerInfoDTO.get().getAddress()).filter(address->!address.isEmpty());
		if(optionalAddress.isPresent()) {
		customerProfile.setAddress(optionalAddress.get());
		}
		Optional<String> optionalState= Optional.ofNullable(optionalCustomerInfoDTO.get().getState()).filter(state->!state.isEmpty());
		if(optionalState.isPresent()) {
		customerProfile.setState(optionalState.get());
		}
		Optional<String> optionalCountry= Optional.ofNullable(optionalCustomerInfoDTO.get().getState()).filter(state->!state.isEmpty());
		if(optionalCountry.isPresent()) {
		customerProfile.setCountry(optionalCountry.get());
		}
		Optional<String> optionalPanNo= Optional.ofNullable(optionalCustomerInfoDTO.get().getPanNo()).filter(panNo->!panNo.isEmpty());
		if(optionalPanNo.isPresent()) {
		customerProfile.setPanNo(optionalPanNo.get());
		}
		Optional<Date> optionalDob= Optional.ofNullable(optionalCustomerInfoDTO.get().getDob());
		if(optionalDob.isPresent()) {
		customerProfile.setDob(optionalDob.get());
		}
		Optional<Long> optionalAcctno= Optional.ofNullable(optionalCustomerInfoDTO.get().getAccountNo());
		if( optionalAcctno.isPresent()) {
		customerProfile.setAccountNo(optionalAcctno.get());
		}
		}
		
		logger.info("customer profile service update method..");
		customerProfileRepository.saveAndFlush(customerProfile);		
	}
	
}

