/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.service;

import com.sk.group.ms.organization.request.OrganizationDataRequest;
import com.sk.group.shared.entity.OrganizationData;

/**
@author - Shreyans Khobare
*/
public interface OrganizationDataService {

	/**
	 * In this method, we are adding a new entry in ORGANIZATION_DATA table.
	 * @param request
	 * @return
	 */
	public OrganizationData addOrganizationData(OrganizationDataRequest request);
	
	/**
	 * In this method, we fetch values from ORGANIZATION_DATA table based on Primary Keys
	 * @param request
	 * @return
	 */
	public OrganizationData getOrganizationData(OrganizationDataRequest request);
	
	/**
	 * In this method, we delete values from ORGANIZATION_DATA table based on Primary Keys
	 * @param request
	 */
	public void deleteOrganizationData(OrganizationDataRequest request);
	
	/**
	 * In this method, we fetch all entries from ORGANIZATION_DATA table
	 * @return
	 */
	public Iterable<OrganizationData> getAllOrganizations();
	
}
