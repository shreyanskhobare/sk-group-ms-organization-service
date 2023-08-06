/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.controller;

import static com.sk.group.shared.implementation.feign.FeignClientConstants.ORGANIZATION_SERVICE_BASE_MAPPING;
import static com.sk.group.shared.implementation.feign.FeignClientConstants.ORGANIZATION_SERVICE_DELETE_ORGANIZATION;
import static com.sk.group.shared.implementation.feign.FeignClientConstants.ORGANIZATION_SERVICE_GET_ALL_ORGANIZATIONS;
import static com.sk.group.shared.implementation.feign.FeignClientConstants.ORGANIZATION_SERVICE_GET_ORGANIZATION;
import static com.sk.group.shared.implementation.feign.FeignClientConstants.ORGANIZATION_SERVICE_SAVE_ORGANIZATION;

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
import com.sk.group.ms.organization.validator.OrganizationServiceValidator;
import com.sk.group.shared.implementation.exception.GroupException;
import com.sk.group.shared.implementation.organization.response.DeleteOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.GetAllOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.GetOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.SaveOrganizationResponse;

/**
 * @author - Shreyans Khobare
 */
@RefreshScope
@RestController
@RequestMapping(ORGANIZATION_SERVICE_BASE_MAPPING)
public class OrganizationServiceController {

	@Autowired
	private OrganizationDataService organizationDataService;
	
	@Autowired
	private OrganizationServiceValidator requestValidator;

	@PostMapping(value = ORGANIZATION_SERVICE_SAVE_ORGANIZATION)
	public ResponseEntity<SaveOrganizationResponse> addOrganization(@RequestBody OrganizationDataRequest request) throws GroupException {

		requestValidator.validateSaveOrganizationData(request);
		SaveOrganizationResponse response = organizationDataService.addOrganizationData(request);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping(value = ORGANIZATION_SERVICE_GET_ORGANIZATION)
	public ResponseEntity<GetOrganizationResponse> getOrganization(
			@PathVariable("organizationId") String organizationId) throws GroupException {

		// Add validation logic here.
		OrganizationDataRequest request = new OrganizationDataRequest();
		request.setOrganizationId(Long.parseLong(organizationId));
		GetOrganizationResponse response = organizationDataService.getOrganizationData(request);

		return ResponseEntity.ok(response);

	}

	@DeleteMapping(value = ORGANIZATION_SERVICE_DELETE_ORGANIZATION)
	public ResponseEntity<DeleteOrganizationResponse> deleteOrganization(@RequestBody OrganizationDataRequest request) throws GroupException {

		requestValidator.validateDeleteOrganization(request);
		DeleteOrganizationResponse response = organizationDataService.deleteOrganizationData(request);

		return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

	}

	@GetMapping(value = ORGANIZATION_SERVICE_GET_ALL_ORGANIZATIONS)
	public ResponseEntity<GetAllOrganizationResponse> getAllOrganizations() {
		
		GetAllOrganizationResponse response = organizationDataService.getAllOrganizations();
		
		return ResponseEntity.ok(response);
		
	}
}
