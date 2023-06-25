/*
Copyright [2023] Shreyans Dilip Khobare
Proof of concept for Code Template
*/
package com.sk.group.ms.organization.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sk.group.shared.entity.OrganizationData;

/**
@author - Shreyans Khobare
*/
@Repository
public interface OrganizationDataRespository extends CrudRepository<OrganizationData, Long>{

}
