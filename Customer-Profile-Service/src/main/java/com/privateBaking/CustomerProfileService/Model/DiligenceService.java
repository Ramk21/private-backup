package com.privateBaking.CustomerProfileService.Model;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "edge-zuul",contextId="diligence-service")
//@RibbonClient(name = "diligence-service")
public interface DiligenceService  {

	@GetMapping(value = "/diligence-service/customer/{customerId}/getStatus")
	public CustomerProfile getDiligenceStatus(@PathVariable Long customerId);
}
