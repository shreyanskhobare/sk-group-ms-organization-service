/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sk.group.ms.organization.constants.OrganizationServiceConstants;
import com.sk.group.ms.organization.repository.OrganizationDataRespository;
import com.sk.group.ms.organization.service.OrganizationDataService;
import com.sk.group.shared.entity.OrganizationData;
import com.sk.group.shared.implementation.employee.request.FilterEmployeesRequest;
import com.sk.group.shared.implementation.employee.response.FilterEmployeesResponse;
import com.sk.group.shared.implementation.exception.GroupException;
import com.sk.group.shared.implementation.feign.EmployeeServiceFeignClient;
import com.sk.group.shared.implementation.organization.request.OrganizationDataRequest;
import com.sk.group.shared.implementation.organization.response.DeleteOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.GetAllOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.GetAllOrganizationResponse.Organization;
import com.sk.group.shared.implementation.organization.response.GetOrganizationEmployeesResponse;
import com.sk.group.shared.implementation.organization.response.GetOrganizationResponse;
import com.sk.group.shared.implementation.organization.response.SaveOrganizationResponse;

/**
 * @author - Shreyans Khobare
 */
@Service
public class OrganizationDataServiceImpl implements OrganizationDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationDataServiceImpl.class);

	@Value("${message.organization.save.success:Organization save successful. OrganizationId is }")
	private String messageSaveSuccessful;

	@Value("${message.no.organization.found:No Organization found for OrganizationId- }")
	private String messageOrganizationNotFound;

	@Value("${message.organization.delete.success:Organization delete successful}")
	private String deletedSuccessMessage;
	
	@Value("${message.organization.employee.not.found:No employees found for organization}")
	private String noEmployeeFound;

	@Autowired
	private OrganizationDataRespository repository;
	
	@Autowired
	private EmployeeServiceFeignClient employeeServiceFeignClient;

	/**
	 * In this method, we are adding a new entry in ORGANIZATION_DATA table.
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public SaveOrganizationResponse addOrganizationData(OrganizationDataRequest request) {

		OrganizationData organizationData = OrganizationData.builder().organizationName(request.getOrganizationName().trim())
				.build();
		organizationData = repository.save(organizationData);

		SaveOrganizationResponse response = new SaveOrganizationResponse();
		response.setOrganizationId(organizationData.getOrganizationId());
		response.setSuccessMessage(messageSaveSuccessful + organizationData.getOrganizationId());

		return response;

	}

	/**
	 * In this method, we fetch values from ORGANIZATION_DATA table based on Primary
	 * Keys
	 * 
	 * @param request
	 * @return
	 * @throws GroupException
	 */
	@Override
	public GetOrganizationResponse getOrganizationData(OrganizationDataRequest request) throws GroupException {

		OrganizationData organizationData = null;
		try {

			organizationData = repository.findById(request.getOrganizationId()).get();

		} catch (Exception e) {
			// NO ROWS Returned will also be thrown as an exception
			LOGGER.error(
					"Exception occured while trying to fetch entries from ORGANIZATION table for OrganizationId: " + request.getOrganizationId(), e);
			throw new GroupException(HttpStatus.NOT_FOUND, OrganizationServiceConstants.ORGANIZATION_NOT_FOUND,
					messageOrganizationNotFound + request.getOrganizationId());
			
		}

		GetOrganizationResponse response = new GetOrganizationResponse();
		response.setOrganizationId(organizationData.getOrganizationId());
		response.setOrganizationName(organizationData.getOrganizationName());

		return response;

	}

	/**
	 * In this method, we delete values from ORGANIZATION_DATA table based on
	 * Primary Keys
	 * 
	 * @param request
	 */
	@Override
	public DeleteOrganizationResponse deleteOrganizationData(OrganizationDataRequest request) {

		repository.deleteAllById(request.getOrganizationIdList());
		DeleteOrganizationResponse response = new DeleteOrganizationResponse();
		response.setSuccessMessage(deletedSuccessMessage);
		return response;

	}

	/**
	 * In this method, we fetch all entries from ORGANIZATION_DATA table
	 * 
	 * @return
	 */
	@Override
	public GetAllOrganizationResponse getAllOrganizations() {

		Iterable<OrganizationData> organizationList = repository.findAll();

		GetAllOrganizationResponse response = new GetAllOrganizationResponse();
		if (!IterableUtils.isEmpty(organizationList)) {

			LOGGER.debug(
					"Number of organizations fetched from ORGANIZATION table: " + IterableUtils.size(organizationList));
			List<Organization> organizations = new ArrayList<>();

			for (OrganizationData data : organizationList) {

				Organization organization = new Organization();
				organization.setOrganizationId(data.getOrganizationId());
				organization.setOrganizationName(data.getOrganizationName());
				organizations.add(organization);

			}

			response.setOrganizations(organizations);

		} else {
			LOGGER.info("No entries fetched from ORGANIZATION table");
		}

		return response;

	}
	
	/**
	 * In this method, we call the '/filterEmployee' endpoint of Employee Service in order to fetch employees beloning to selected organizationIds
	 * @param request
	 * @return
	 * @throws GroupException
	 */
	public GetOrganizationEmployeesResponse getOrganizationEmployees(OrganizationDataRequest request) throws GroupException {
		
		FilterEmployeesRequest employeeRequest = new FilterEmployeesRequest();
		employeeRequest.setOrganizationId(request.getOrganizationIdList());
		employeeRequest.setEmploymentActive(true);
		employeeRequest.setReturnEmployeePersonalInfo(false);
		
		FilterEmployeesResponse employeeResponse = employeeServiceFeignClient.filterEmployees(employeeRequest);
		
		if (null == employeeResponse || CollectionUtils.isEmpty(employeeResponse.getEmployeeList())) {
			
			LOGGER.info("Received no employees in response for organizationId(s): " + request.getOrganizationIdList());
			throw new GroupException(HttpStatus.NOT_FOUND, null, noEmployeeFound);
			
		} else {
			
			LOGGER.info("Received [" + employeeResponse.getEmployeeList().size()
					+ "] employees in response for organizationId(s): " + request.getOrganizationIdList());
		
			GetOrganizationEmployeesResponse response = new GetOrganizationEmployeesResponse();
			response.setEmployeeList(employeeResponse.getEmployeeList());
			return response;
			
		}
		
	}

}
