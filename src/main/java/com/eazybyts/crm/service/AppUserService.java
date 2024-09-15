package com.eazybyts.crm.service;

import java.util.List;
import java.util.Map;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.AppUserDto;

public interface AppUserService {
	public ApiResponseDto createUser(AppUserDto userDto);
	public AppUserDto fetchAppUserDetails(Long userId);
	public List<AppUserDto> findAllUsersByNameAndParentId(String name,Long parentId);
	public List<AppUserDto> findAllUsersByParentId(Long parentId);
	public AppUserDto findUserByEmail(String email);
	public boolean doesEmailExists(String email);
	public Map<String, List<String>> populateAccPageDropdownValues();
}
