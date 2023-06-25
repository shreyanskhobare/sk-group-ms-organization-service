/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.group.ms.organization.repository.OrganizationDataRespository;
import com.sk.group.ms.organization.request.OrganizationDataRequest;
import com.sk.group.ms.organization.service.OrganizationDataService;
import com.sk.group.shared.entity.OrganizationData;

/**
 * @author - Shreyans Khobare
 */
@Service
public class OrganizationDataServiceImpl implements OrganizationDataService {

	@Autowired
	private OrganizationDataRespository repository;

	/**
	 * In this method, we are adding a new entry in ORGANIZATION_DATA table.
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public OrganizationData addOrganizationData(OrganizationDataRequest request) {

		OrganizationData organizationData = OrganizationData.builder().organizationName(request.getOrganizationName())
				.build();
		organizationData = repository.save(organizationData);
		return organizationData;

	}

	/**
	 * In this method, we fetch values from ORGANIZATION_DATA table based on Primary
	 * Keys
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public OrganizationData getOrganizationData(OrganizationDataRequest request) {

		return repository.findById(request.getOrganizationId()).get();

	}

	/**
	 * In this method, we delete values from ORGANIZATION_DATA table based on
	 * Primary Keys
	 * 
	 * @param request
	 */
	@Override
	public void deleteOrganizationData(OrganizationDataRequest request) {

		repository.deleteAllById(request.getDeleteRequests());

	}
	
	/**
	 * In this method, we fetch all entries from ORGANIZATION_DATA table
	 * @return
	 */
	public Iterable<OrganizationData> getAllOrganizations() {
		
		return repository.findAll();
		
	}

}
