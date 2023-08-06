/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.validator.impl;

import static com.sk.group.shared.implementation.feign.FeignClientConstants.ORGANIZATION_SERVICE_DELETE_ORGANIZATION;
import static com.sk.group.shared.implementation.feign.FeignClientConstants.ORGANIZATION_SERVICE_SAVE_ORGANIZATION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sk.group.ms.organization.request.OrganizationDataRequest;
import com.sk.group.ms.organization.validator.OrganizationServiceValidator;
import com.sk.group.shared.implementation.exception.ControllerRequestValidationFailureException;
import com.sk.group.shared.implementation.exception.GroupErrorCodes;

import io.micrometer.common.util.StringUtils;

/**
 * @author - Shreyans Khobare
 */
@Service
public class OrganizationServiceValidatorImpl implements OrganizationServiceValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceValidatorImpl.class);

	@Value("${message.controller.request.class.empty:Request Class is empty}")
	private String requestClassEmpty;

	@Value("${message.controller.request.parameter.missing:Missing mandatory request parameter}")
	private String requestParameterMissing;

	/**
	 * In this method, we validate the request parameters of '/saveOrganizationData'
	 * endpoint
	 * 
	 * @param request
	 * @throws ControllerRequestValidationFailureException
	 */
	public void validateSaveOrganizationData(OrganizationDataRequest request)
			throws ControllerRequestValidationFailureException {

		if (null == request) {
			requestClassEmpty(ORGANIZATION_SERVICE_SAVE_ORGANIZATION);
		}

		if (StringUtils.isBlank(request.getOrganizationName())) {
			missingParameterException("organizationName", ORGANIZATION_SERVICE_SAVE_ORGANIZATION);
		}

	}

	/**
	 * In this method, we validate the request parameters of '/deleteOrganization'
	 * endpoint
	 * 
	 * @param request
	 * @throws ControllerRequestValidationFailureException
	 */
	public void validateDeleteOrganization(OrganizationDataRequest request)
			throws ControllerRequestValidationFailureException {

		if (null == request || (StringUtils.isBlank(request.getOrganizationName())
				&& CollectionUtils.isEmpty(request.getDeleteRequests()) && null == request.getOrganizationId())) {
			requestClassEmpty(ORGANIZATION_SERVICE_DELETE_ORGANIZATION);
		}

	}

	/**
	 * Common method for throwing REQUEST_PARAMETER_MISSING exception.
	 * 
	 * @param field
	 * @throws ControllerRequestValidationFailureException
	 */
	private void missingParameterException(String field, String endpoint)
			throws ControllerRequestValidationFailureException {

		LOGGER.error("Mandatory field [" + field + "] is missing in request of [" + endpoint + "]");
		throw new ControllerRequestValidationFailureException(GroupErrorCodes.REQUEST_PARAMETER_MISSING,
				requestParameterMissing);

	}

	/**
	 * Common method for throwing REQUEST_OBJECT_EMPTY exception
	 * 
	 * @param endpoint
	 * @throws ControllerRequestValidationFailureException
	 */
	private void requestClassEmpty(String endpoint) throws ControllerRequestValidationFailureException {

		LOGGER.error("Request class of [" + endpoint + "] is null");
		throw new ControllerRequestValidationFailureException(GroupErrorCodes.REQUEST_OBJECT_EMPTY, requestClassEmpty);

	}

}
