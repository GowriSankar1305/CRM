package com.eazybyts.crm.service;

import java.util.List;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.SalesDto;

public interface SalesService {
	public ApiResponseDto addSales(SalesDto salesDto);
	public SalesDto findSalesById(Long salesId);
	public List<SalesDto> findSalesByAccountId(Long userId);
	public List<SalesDto> findSalesByContactId(Long contactId);
	public List<SalesDto> findSalesByUserId(Long userId);
}
