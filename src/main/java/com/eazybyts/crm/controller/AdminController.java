package com.eazybyts.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.AppUserDto;
import com.eazybyts.crm.dto.LoginRequestDto;
import com.eazybyts.crm.dto.LoginResponseDto;
import com.eazybyts.crm.enums.UserRoleEnum;
import com.eazybyts.crm.service.AppUserService;
import com.eazybyts.crm.util.CrmUtility;
import com.eazybyts.crm.util.JwtUtility;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/crm/admin/")
public class AdminController {

	@Autowired
	private AppUserService appUserService;
	@Autowired
	private CrmUtility crmUtility;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtility jwtUtility;

	@PostMapping("create")
	public ApiResponseDto createAdmin(@RequestBody AppUserDto adminDto) {
		crmUtility.validateNewAdminRequest(adminDto);
		adminDto.setUserRole(UserRoleEnum.ADMIN.name());
		return appUserService.createUser(adminDto);
	}

	@PostMapping("login")
	public LoginResponseDto loginTheUser(@RequestBody @Valid LoginRequestDto loginRequestDto) throws Exception {
		log.info("***** authenticating user *****");
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				loginRequestDto.getUserName(), loginRequestDto.getUserPassword());
		Authentication authResponse = authenticationManager.authenticate(authentication);
		log.info("authorities-----> {}", authResponse.getAuthorities());
		log.info("name-----> {}", authResponse.getName());
		AppUserDto appUserDto = appUserService.findUserByEmail(authResponse.getName());
		String token = jwtUtility.generateToken(appUserDto.getEmailId(), appUserDto.getUserId());

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setToken(token);
		loginResponseDto.setUserId(appUserDto.getUserId());
		loginResponseDto.setUserEmail(appUserDto.getEmailId());
		return loginResponseDto;
	}
}
