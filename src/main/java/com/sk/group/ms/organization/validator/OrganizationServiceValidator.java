/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.validator;

import com.sk.group.shared.implementation.exception.ControllerRequestValidationFailureException;
import com.sk.group.shared.implementation.organization.request.OrganizationDataRequest;

/**
@author - Shreyans Khobare
*/
public interface OrganizationServiceValidator {

	/**
	 * In this method, we validate the request parameters of '/saveOrganizationData' endpoint
	 * @param request
	 * @throws ControllerRequestValidationFailureException
	 */
	public void validateSaveOrganizationData(OrganizationDataRequest request) throws ControllerRequestValidationFailureException;

	/**
	 * In this method, we validate the request parameters of '/deleteOrganization' endpoint
	 * @param request
	 * @throws ControllerRequestValidationFailureException
	 */
	public void validateDeleteOrganization(OrganizationDataRequest request) throws ControllerRequestValidationFailureException;

	/**
	 * In this method, we validate the request parameters of '/getOrganizationEmployees' endpoint
	 * @param request
	 * @throws ControllerRequestValidationFailureException
	 */
	public void validateGetOrganizationEmployees(OrganizationDataRequest request) throws ControllerRequestValidationFailureException;

}
