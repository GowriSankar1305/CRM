package com.eazybyts.crm.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.eazybyts.crm.dto.AccountDto;
import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.entity.Account;
import com.eazybyts.crm.enums.AccountTypeEnum;
import com.eazybyts.crm.enums.IndustryTypeEnum;
import com.eazybyts.crm.enums.OwnershipTypeEnum;
import com.eazybyts.crm.exception.InvalidInputException;
import com.eazybyts.crm.repository.AccountRepository;
import com.eazybyts.crm.repository.AppUserRepository;
import com.eazybyts.crm.service.AccountService;
import com.eazybyts.crm.service.AddressService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AddressService addressService;
	
	@Override
	@Transactional
	public ApiResponseDto saveAccount(AccountDto accountDto) {
		accountRepository.save(dtoToEntity(accountDto));
		return new ApiResponseDto("Account added successfully!", 201,null);
	}

	@Override
	public AccountDto findAccount(Long accountId) {
		Account account = accountRepository.findByAccountId(accountId);
		if(Objects.isNull(account))	{
			throw new InvalidInputException("Account not found with id: " + accountId);
		}
		return entityToDto(account);
	}

	@Override
	public List<AccountDto> findAllAccounts(Long ownerId) {
		List<AccountDto> accDtoList = new ArrayList<>();
		List<Account> accEntityList = accountRepository.findByAccountOwner_UserId(ownerId);
		if(!CollectionUtils.isEmpty(accEntityList))	{
			accEntityList.forEach(entity -> accDtoList.add(entityToDto(entity)));
		}
		return accDtoList;
	}
	
	private AccountDto entityToDto(Account entity)	{
		AccountDto dto = new AccountDto();
		dto.setAccountId(entity.getAccountId());
		dto.setAccountNumber(entity.getAccountNumber());
		if(null != entity.getAccountType())	{
			dto.setAccountType(entity.getAccountType().name());
		}
		if(null != entity.getAnnualRevenue())	{
			dto.setAnnualRevenue(entity.getAnnualRevenue().toString());
		}
		dto.setBillingAddress(addressService.entityToDto(entity.getBillingAddress()));
		dto.setShippingAddress(addressService.entityToDto(entity.getShippingAddress()));
		dto.setDescription(entity.getDescription());
		dto.setEmployees(entity.getEmployees());
		dto.setFax(entity.getFax());
		if(null != entity.getIndustry())	{
			dto.setIndustry(entity.getIndustry().name());
		}
		if(null != entity.getOwnerShipType())	{
			dto.setOwnerShipType(entity.getOwnerShipType().name());
		}
		dto.setPhone(entity.getPhone());
		dto.setSicCode(entity.getSicCode());
		dto.setWebsite(entity.getWebsite());
		return dto;
	}
	
	private Account dtoToEntity(AccountDto dto)	{
		Account entity = new Account();
		entity.setAccountNumber(dto.getAccountNumber());
		entity.setAccountOwner(appUserRepository.findByUserId(dto.getOwnerId()));
		if(StringUtils.hasText(dto.getAccountType()))	{
			entity.setAccountType(AccountTypeEnum.valueOf(dto.getAccountType()));
		}
		if(StringUtils.hasText(dto.getAnnualRevenue()))	{
			entity.setAnnualRevenue(new BigDecimal(dto.getAnnualRevenue()));
		}
		entity.setBillingAddress(addressService.dtoToEntity(dto.getBillingAddress()));
		entity.setDescription(dto.getDescription());
		entity.setEmployees(dto.getEmployees());
		entity.setFax(dto.getFax());
		if(StringUtils.hasText(dto.getIndustry()))	{
			entity.setIndustry(IndustryTypeEnum.valueOf(dto.getIndustry()));
		}
		if(StringUtils.hasText(dto.getOwnerShipType()))	{
			entity.setOwnerShipType(OwnershipTypeEnum.valueOf(dto.getOwnerShipType()));
		}
		entity.setPhone(dto.getPhone());
		entity.setShippingAddress(addressService.dtoToEntity(dto.getShippingAddress()));
		entity.setSicCode(dto.getSicCode());
		entity.setWebsite(dto.getWebsite());
		entity.setCreatedTime(LocalDateTime.now());
		entity.setModifiedTime(LocalDateTime.now());
		entity.setAccountName(dto.getAccountName());
		return entity;
	}

	@Override
	public boolean doesAccountNameExists(String accName) {
		return accountRepository.findByAccountName(accName) != null ? true : false;
	}

	@Override
	public List<AccountDto> findAllAccountsOfUser(Long userId) {
		List<AccountDto> dtoList = new ArrayList<>();
		List<Object[]> accounts = accountRepository.findAllAccountsOfAdminUser(userId);
		if(!CollectionUtils.isEmpty(accounts)) {
			accounts.forEach(acc -> {
				AccountDto dto = new AccountDto();
				dto.setAccountName((String)acc[0]);
				dto.setPhone((String)acc[1]);
				dto.setWebsite((String)acc[2]);
				dto.setAccountNumber((String)acc[3]);
				dtoList.add(dto);
			});
		}
		return dtoList;
	}
}
