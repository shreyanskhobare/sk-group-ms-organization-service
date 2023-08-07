/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.service;

import com.sk.group.shared.implementation.exception.GroupException;
import com.sk.group.shared.implementation.organization.request.OrganizationDataRequest;
import com.sk.group.shared.implementation.organization.response.DeleteOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.GetAllOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.GetOrganizationEmployeesResponse;
import com.sk.group.shared.implementation.organization.response.GetOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.SaveOrganizationResponse;

/**
@author - Shreyans Khobare
*/
public interface OrganizationDataService {

	/**
	 * In this method, we are adding a new entry in ORGANIZATION_DATA table.
	 * @param request
	 * @return
	 */
	public SaveOrganizationResponse addOrganizationData(OrganizationDataRequest request);
	
	/**
	 * In this method, we fetch values from ORGANIZATION_DATA table based on Primary Keys
	 * @param request
	 * @return
	 */
	public GetOrganizationResponse getOrganizationData(OrganizationDataRequest request) throws GroupException;
	
	/**
	 * In this method, we delete values from ORGANIZATION_DATA table based on Primary Keys
	 * @param request
	 * @return 
	 */
	public DeleteOrganizationResponse deleteOrganizationData(OrganizationDataRequest request);
	
	/**
	 * In this method, we fetch all entries from ORGANIZATION_DATA table
	 * @return
	 */
	public GetAllOrganizationResponse getAllOrganizations();

	/**
	 * In this method, we call the '/filterEmployee' endpoint of Employee Service in order to fetch employees beloning to selected organizationIds
	 * @param request
	 * @return
	 * @throws GroupException
	 */
	public GetOrganizationEmployeesResponse getOrganizationEmployees(OrganizationDataRequest request) throws GroupException;
	
}
