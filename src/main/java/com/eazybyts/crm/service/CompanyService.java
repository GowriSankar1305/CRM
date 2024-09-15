package com.eazybyts.crm.service;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.CompanyDto;

public interface CompanyService {
	public ApiResponseDto saveCompany(CompanyDto companyDto);
	public CompanyDto findCompany(Long comapnyId);
}
