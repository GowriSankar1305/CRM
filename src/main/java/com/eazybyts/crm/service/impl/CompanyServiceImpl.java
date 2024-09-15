package com.eazybyts.crm.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.CompanyDto;
import com.eazybyts.crm.entity.Company;
import com.eazybyts.crm.exception.InvalidInputException;
import com.eazybyts.crm.repository.AppUserRepository;
import com.eazybyts.crm.repository.CompanyRepository;
import com.eazybyts.crm.service.AddressService;
import com.eazybyts.crm.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private AddressService addressService;
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Override
	public ApiResponseDto saveCompany(CompanyDto companyDto) {
		companyRepository.save(dtoToEntity(companyDto));
		return new ApiResponseDto("Company added successfully!", 201,null);
	}
	
	private Company dtoToEntity(CompanyDto dto)	{
		Company entity = new Company();
		entity.setAddress(addressService.dtoToEntity(dto.getAddress()));
		entity.setCompanyAlias(dto.getCompanyAlias());
		entity.setCompanyName(dto.getCompanyName());
		entity.setDescription(dto.getDescription());
		entity.setFax(dto.getFax());
		entity.setMobileNo(dto.getMobileNo());
		entity.setNoOfEmployees(dto.getNoOfEmployees());
		entity.setOwner(appUserRepository.findByUserId(dto.getOwnerId()));
		entity.setPhoneNo(dto.getPhoneNo());
		entity.setWebsiteUrl(dto.getWebsiteUrl());
		entity.setCreatedTime(LocalDateTime.now());
		entity.setModifiedTime(LocalDateTime.now());
		return entity;
	}
	
	private CompanyDto entityToDto(Company entity)	{
		CompanyDto dto = new CompanyDto();
		dto.setAddress(addressService.entityToDto(entity.getAddress()));
		dto.setCompanyAlias(entity.getCompanyAlias());
		dto.setCompanyId(entity.getCompanyId());
		dto.setCompanyName(entity.getCompanyName());
		dto.setDescription(entity.getDescription());
		dto.setFax(entity.getFax());
		dto.setMobileNo(entity.getMobileNo());
		dto.setNoOfEmployees(entity.getNoOfEmployees());
		if(Objects.nonNull(entity.getOwner()))	{
			dto.setOwnerId(entity.getOwner().getUserId());	
		}
		dto.setPhoneNo(entity.getPhoneNo());
		dto.setWebsiteUrl(entity.getWebsiteUrl());
		return dto;
	}
	
	@Override
	public CompanyDto findCompany(Long comapnyId) {
		Company company = companyRepository.findByCompanyId(comapnyId);
		if(Objects.isNull(company))	{
			throw new InvalidInputException("Company not found with id: " + comapnyId);
		}
		return entityToDto(company);
	}

}
