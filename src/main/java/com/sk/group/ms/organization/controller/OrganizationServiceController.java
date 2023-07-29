/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.sk.group.shared.implementation.exception.GroupException;
import com.sk.group.shared.implementation.response.organization.DeleteOrganizationResponse;
import com.sk.group.shared.implementation.response.organization.GetAllOrganizationResponse;
import com.sk.group.shared.implementation.response.organization.GetOrganizationResponse;
import com.sk.group.shared.implementation.response.organization.SaveOrganizationResponse;

/**
 * @author - Shreyans Khobare
 */
@RefreshScope
@RestController
@RequestMapping("api/organization-service")
public class OrganizationServiceController {

	@Autowired
	private OrganizationDataService organizationDataService;

	@PostMapping(value = "/saveOrganizationData")
	public ResponseEntity<SaveOrganizationResponse> addOrganization(@RequestBody OrganizationDataRequest request) {

		// Add validation logic here.
		SaveOrganizationResponse response = organizationDataService.addOrganizationData(request);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping(value = "/getOrganization/{organizationId}")
	public ResponseEntity<GetOrganizationResponse> getOrganization(
			@PathVariable("organizationId") String organizationId) throws GroupException {

		// Add validation logic here.
		OrganizationDataRequest request = new OrganizationDataRequest();
		request.setOrganizationId(Long.parseLong(organizationId));
		GetOrganizationResponse response = organizationDataService.getOrganizationData(request);

		return ResponseEntity.ok(response);

	}

	@DeleteMapping(value = "/deleteOrganization")
	public ResponseEntity<DeleteOrganizationResponse> deleteOrganization(@RequestBody OrganizationDataRequest request) {

		// Add validation logic here.
		DeleteOrganizationResponse response = organizationDataService.deleteOrganizationData(request);

		return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

	}

	@GetMapping(value = "/getAllOrganizations")
	public ResponseEntity<GetAllOrganizationResponse> getAllOrganizations() {
		
		GetAllOrganizationResponse response = organizationDataService.getAllOrganizations();
		
		return ResponseEntity.ok(response);
		
	}
}
