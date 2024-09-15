package com.eazybyts.crm.service;

import java.util.List;
import java.util.Map;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.VendorDto;

public interface VendorService {
	public ApiResponseDto createVendor(VendorDto vendorDto);
	public VendorDto findVendor(Long vendorId);
	public List<VendorDto> findVendorsByOwner(Long ownerId);
	public List<VendorDto> findVendorsByUser(Long userId);
	public boolean doesVendorNameExists(String vendorName);
	public boolean doesVendorEmailExists(String email);
	public Map<String, List<String>> populateVendorPgDdValues();
}
