package com.eazybyts.crm.service;

import java.util.List;
import java.util.Map;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.ContactDto;

public interface ContactService {
	public ApiResponseDto addContact(ContactDto contactDto);
	public ContactDto findContact(Long contactId);
	public List<ContactDto> findContactsByAccountId(Long accountId);
	public List<ContactDto> findContactsByOwnerId(Long ownerId);
	public Map<String, List<String>> populateContactPgDdValues();
	public boolean doesEmailIdExists(String email);
}
