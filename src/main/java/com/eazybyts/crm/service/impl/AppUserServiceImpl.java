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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.AppUserDto;
import com.eazybyts.crm.entity.AppUser;
import com.eazybyts.crm.enums.AccountTypeEnum;
import com.eazybyts.crm.enums.IndustryTypeEnum;
import com.eazybyts.crm.enums.OwnershipTypeEnum;
import com.eazybyts.crm.enums.UserRoleEnum;
import com.eazybyts.crm.exception.InvalidInputException;
import com.eazybyts.crm.repository.AppUserRepository;
import com.eazybyts.crm.service.AddressService;
import com.eazybyts.crm.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AddressService addressService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public ApiResponseDto createUser(AppUserDto userDto) {
		appUserRepository.save(dtoToEntity(userDto));
		return new ApiResponseDto("User created successfully!", 201,null);
	}

	private AppUser dtoToEntity (AppUserDto appUserDto)	{
		AppUser user = new AppUser();
		user.setAlias(appUserDto.getAlias());
		user.setDateOfBirth(appUserDto.getDateOfBirth());
		user.setEmailId(appUserDto.getEmailId());
		user.setFaxNo(appUserDto.getFaxNo());
		user.setFirstName(appUserDto.getFirstName());
		user.setLastName(appUserDto.getLastName());
		user.setMobileNo(appUserDto.getMobileNo());
		user.setPhoneNo(appUserDto.getPhoneNo());
		user.setUserRole(UserRoleEnum.valueOf(appUserDto.getUserRole()));
		user.setWebsiteUrl(appUserDto.getWebsiteUrl());
		user.setCreatedTime(LocalDateTime.now());
		user.setModifiedTime(LocalDateTime.now());
		user.setParentId(appUserDto.getParentId());
		user.setAddress(addressService.dtoToEntity(appUserDto.getAddress()));
		user.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
		return user;
	}
	
	private AppUserDto entityToDto(AppUser entity) {
		AppUserDto dto = new AppUserDto();
		if(Objects.isNull(entity))	{
			return dto;
		}
		dto.setAddress(addressService.entityToDto(entity.getAddress()));
		dto.setAlias(entity.getAlias());
		dto.setAlias(entity.getAlias());
		dto.setDateOfBirth(entity.getDateOfBirth());
		dto.setEmailId(entity.getEmailId());
		dto.setFaxNo(entity.getFaxNo());
		dto.setFirstName(entity.getFirstName());
		dto.setFullName(entity.getFullName());
		dto.setLastName(entity.getFullName());
		dto.setMobileNo(entity.getMobileNo());
		dto.setPhoneNo(entity.getPhoneNo());
		dto.setUserId(entity.getUserId());
		dto.setUserRole(entity.getUserRole().name());
		dto.setWebsiteUrl(entity.getWebsiteUrl());
		dto.setParentId(entity.getParentId());
		return dto;
	}
	
	@Override
	public AppUserDto fetchAppUserDetails(Long userId) {
		AppUser appUser = appUserRepository.findByUserId(userId);
		if(Objects.isNull(appUser))	{
			throw new InvalidInputException("User not found with id: " + userId);
		}
		AppUserDto dto = new AppUserDto();
		dto.setAddress(addressService.entityToDto(appUser.getAddress()));
		dto.setAlias(appUser.getAlias());
		dto.setDateOfBirth(appUser.getDateOfBirth());
		dto.setEmailId(appUser.getEmailId());
		dto.setFaxNo(appUser.getFaxNo());
		dto.setFirstName(appUser.getFirstName());
		dto.setLastName(appUser.getLastName());
		dto.setMobileNo(appUser.getMobileNo());
		dto.setPhoneNo(appUser.getPhoneNo());
		dto.setUserId(appUser.getUserId());
		dto.setUserRole(appUser.getUserRole().name());
		dto.setWebsiteUrl(appUser.getWebsiteUrl());
		return dto;
	}

	@Override
	public List<AppUserDto> findAllUsersByNameAndParentId(String name,Long parentId) {
		List<AppUserDto> dtoList = new ArrayList<>();
		List<Object[]> users = appUserRepository
				.findAllUsersByNameAndParentId(name,name,parentId);
		if(!CollectionUtils.isEmpty(users))	{
			users.forEach(object -> {
				AppUserDto dto = new AppUserDto();
				dto.setUserId((Long)object[0]);
				dto.setEmailId((String)object[1]);
				dto.setFirstName((String)object[2]);
				dto.setLastName((String)object[3]);
				dto.setMobileNo((String)object[4]);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	@Transactional
	public AppUserDto findUserByEmail(String email) {
		return entityToDto(appUserRepository.findByEmailId(email));
	}

	@Override
	public boolean doesEmailExists(String email) {
		return appUserRepository.findByEmailId(email) != null ? true : false;
	}

	@Override
	public List<AppUserDto> findAllUsersByParentId(Long parentId) {
		List<AppUserDto> dtoList = new ArrayList<>();
		List<Object[]> users = appUserRepository.findAllUsersByParentId(parentId);
		if(!CollectionUtils.isEmpty(users))	{
			users.forEach(object -> {
				AppUserDto dto = new AppUserDto();
				dto.setUserId((Long)object[0]);
				dto.setEmailId((String)object[1]);
				dto.setFirstName((String)object[2]);
				dto.setLastName((String)object[3]);
				dto.setMobileNo((String)object[4]);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}

	@Override
	public Map<String, List<String>> populateAccPageDropdownValues() {
		Map<String, List<String>> dropdowns = new HashMap<>();
		dropdowns.put("accTypes", Stream.of(AccountTypeEnum.values()).map(accType 
				-> accType.name()).collect(Collectors.toList()));
		dropdowns.put("industryTypes", Stream.of(IndustryTypeEnum.values()).map(indType 
				-> indType.name()).collect(Collectors.toList()));
		dropdowns.put("ownershipTypes", Stream.of(OwnershipTypeEnum.values()).map(indType 
				-> indType.name()).collect(Collectors.toList()));
		/*dropdowns.put("salesStages", Stream.of(SalesStageEnum.values()).map(indType 
				-> indType.name()).collect(Collectors.toList()));
		dropdowns.put("taskPriorities", Stream.of(SalesStageEnum.values()).map(indType 
				-> indType.name()).collect(Collectors.toList()));*/
		return dropdowns;
	}

}
