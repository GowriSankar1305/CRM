package com.eazybyts.crm.service;

import java.util.List;

import com.eazybyts.crm.dto.AccountDto;
import com.eazybyts.crm.dto.ApiResponseDto;

public interface AccountService {
	public ApiResponseDto saveAccount(AccountDto accountDto);
	public AccountDto findAccount(Long accountId);
	public List<AccountDto> findAllAccounts(Long ownerId);
	public boolean doesAccountNameExists(String accName);
	public List<AccountDto> findAllAccountsOfUser(Long userId);
}
