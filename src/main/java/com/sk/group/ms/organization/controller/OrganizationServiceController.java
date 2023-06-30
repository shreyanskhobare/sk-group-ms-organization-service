/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.group.ms.organization.request.OrganizationDataRequest;
import com.sk.group.ms.organization.service.OrganizationDataService;
import com.sk.group.shared.entity.OrganizationData;

/**
 * @author - Shreyans Khobare
 */
@RefreshScope
@RestController
@RequestMapping("api/organization-service")
public class OrganizationServiceController {
	
	@Value("${organization.delete.success.message}")
	private String deletedSuccessMessage; 

	@Autowired
	private OrganizationDataService organizationDataService;
	
	@PostMapping(value = "/saveOrganizationData")
	public ResponseEntity<OrganizationData> addOrganization(@RequestBody OrganizationDataRequest request) {
		
		// Add validation logic here.
		OrganizationData response = organizationDataService.addOrganizationData(request);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}
	
	@GetMapping(value = "/getOrganization/{organizationId}")
	public ResponseEntity<OrganizationData> getOrganization(@PathVariable("organizationId") String organizationId) {
		
		// Add validation logic here.
		OrganizationDataRequest request = new OrganizationDataRequest();
		request.setOrganizationId(Long.parseLong(organizationId));
		OrganizationData response = organizationDataService.getOrganizationData(request);
		
		return ResponseEntity.ok(response);
		
	}

	@DeleteMapping(value = "/deleteOrganization")
	public ResponseEntity<String> deleteOrganization(@RequestBody OrganizationDataRequest request) {
		
		// Add validation logic here.
		organizationDataService.deleteOrganizationData(request);
		
		return ResponseEntity.ok(deletedSuccessMessage);
		
	}
	
	@GetMapping(value = "/getAllOrganization")
	public ResponseEntity<Iterable<OrganizationData>> getAllOrganizations() {
		return ResponseEntity.ok(organizationDataService.getAllOrganizations());
	}
}
