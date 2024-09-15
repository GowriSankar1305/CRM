package com.eazybyts.crm.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.VendorDto;
import com.eazybyts.crm.entity.Vendor;
import com.eazybyts.crm.enums.GeneralLedgerEnum;
import com.eazybyts.crm.enums.LeadSourceEnum;
import com.eazybyts.crm.exception.InvalidInputException;
import com.eazybyts.crm.repository.AppUserRepository;
import com.eazybyts.crm.repository.VendorRepository;
import com.eazybyts.crm.service.AddressService;
import com.eazybyts.crm.service.VendorService;
import com.eazybyts.crm.util.ThreadLocalUtility;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private VendorRepository vendorRepository;
	@Autowired
	private AddressService addressService;
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Override
	@Transactional
	public ApiResponseDto createVendor(VendorDto vendorDto) {
		vendorRepository.save(dtoToEntity(vendorDto));
		return new ApiResponseDto("Vendor added successfully!", 201,null);
	}

	@Override
	@Transactional
	public VendorDto findVendor(Long vendorId) {
		Vendor vendor = vendorRepository.findByVendorId(vendorId);
		if(Objects.isNull(vendor))	{
			throw new InvalidInputException("Vendor found with id: " + vendorId);
		}
		return entityToDto(vendor);
	}

	@Override
	@Transactional
	public List<VendorDto> findVendorsByOwner(Long ownerId) {
		List<VendorDto> dtoList = new ArrayList<>();
		List<Vendor> vendorList = vendorRepository.findByVendorOwner_UserId(ownerId);
		if(!CollectionUtils.isEmpty(vendorList))	{
			vendorList.forEach(entity -> entityToDto(entity));
		}
		return dtoList;
	}
	
	private Vendor dtoToEntity(VendorDto dto)	{
		Vendor vendor = new Vendor();
		if(!Objects.isNull(dto))	{
			vendor.setCategory(dto.getCategory());
			vendor.setCreatedTime(LocalDateTime.now());
			vendor.setModifiedTime(LocalDateTime.now());
			vendor.setAddress(addressService.dtoToEntity(dto.getAddress()));
			vendor.setDescription(dto.getDescription());
			vendor.setEmail(dto.getEmail());
			if(dto.getGlAccount() != null)	{
				vendor.setGeneralLedgerAccount(GeneralLedgerEnum.valueOf(dto.getGlAccount()));
			}
			if(StringUtils.hasText(dto.getLeadSource()))	{
				vendor.setLeadSource(LeadSourceEnum.valueOf(dto.getLeadSource()));
			}
			vendor.setPhone(dto.getPhone());
			vendor.setVendorName(dto.getVendorName());
			vendor.setVendorOwner(appUserRepository.findByUserId(dto.getOwnerId()));
			vendor.setWebSite(dto.getWebSite());
			vendor.setCreatedBy((Long)ThreadLocalUtility.get().get("principalId"));
			vendor.setModifiedBy((Long)ThreadLocalUtility.get().get("principalId"));
		}
		return vendor;
	}

	private VendorDto entityToDto(Vendor entity)	{
		VendorDto dto = new VendorDto();
		if(Objects.nonNull(entity.getAddress()))	{
			dto.setAddress(addressService.entityToDto(entity.getAddress()));
		}
		dto.setCategory(entity.getCategory());
		dto.setDescription(entity.getDescription());
		dto.setEmail(entity.getEmail());
		if(Objects.nonNull(entity.getGeneralLedgerAccount()))	{
			dto.setGlAccount(entity.getGeneralLedgerAccount().name());
		}
		if(Objects.nonNull(entity.getLeadSource()))	{
			dto.setLeadSource(entity.getLeadSource().name());
		}
		dto.setPhone(entity.getPhone());
		dto.setVendorId(entity.getVendorId());
		dto.setVendorName(entity.getVendorName());
		dto.setWebSite(entity.getWebSite());
		return dto;
	}

	@Override
	public boolean doesVendorNameExists(String vendorName) {
		return vendorRepository.findByVendorName(vendorName) != null ? true : false;
	}

	@Override
	public boolean doesVendorEmailExists(String email) {
		return vendorRepository.findByEmail(email) != null ? true : false;
	}

	@Override
	@Transactional
	public List<VendorDto> findVendorsByUser(Long userId) {
		List<VendorDto> dtoList = new ArrayList<>();
		List<Vendor> vendorList = vendorRepository.findByCreatedBy(userId);
		if(!CollectionUtils.isEmpty(vendorList))	{
			vendorList.forEach(entity -> dtoList.add(entityToDto(entity)));
		}
		return dtoList;
	}

	@Override
	public Map<String, List<String>> populateVendorPgDdValues() {
		Map<String, List<String>> response = new HashMap<>();
		response.put("ledgers", Stream.of(GeneralLedgerEnum.values())
				.map(gl -> gl.name()).collect(Collectors.toList()));
		return response;
	}
}
