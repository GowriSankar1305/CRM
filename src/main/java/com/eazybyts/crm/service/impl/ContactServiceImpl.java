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

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.ContactDto;
import com.eazybyts.crm.entity.Contact;
import com.eazybyts.crm.enums.LeadSourceEnum;
import com.eazybyts.crm.enums.SalutationEnum;
import com.eazybyts.crm.exception.InvalidInputException;
import com.eazybyts.crm.repository.AccountRepository;
import com.eazybyts.crm.repository.AppUserRepository;
import com.eazybyts.crm.repository.ContactRepository;
import com.eazybyts.crm.service.AddressService;
import com.eazybyts.crm.service.ContactService;
import com.eazybyts.crm.util.ThreadLocalUtility;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AddressService addressService;
	
	@Override
	@Transactional
	public ApiResponseDto addContact(ContactDto contactDto) {
		Contact contact = dtoToEntity(contactDto);
		contact.setCreatedBy((Long)ThreadLocalUtility.get().get("principalId"));
		contact.setModifiedBy((Long)ThreadLocalUtility.get().get("principalId"));
		contactRepository.save(contact);
		return new ApiResponseDto("Contact added successfully!", 201,null);
	}

	@Override
	public ContactDto findContact(Long contactId) {
		Contact contact = contactRepository.findByContactId(contactId);
		if(Objects.isNull(contact))	{
			throw new InvalidInputException("Contact not found with id: " + contactId);
		}
		return entityToDto(contact);
	}

	@Override
	public List<ContactDto> findContactsByAccountId(Long accountId) {
		List<ContactDto> contactDtoList = new ArrayList<>();
		List<Contact> contactList = contactRepository.findByAccount_AccountId(accountId);
		if(CollectionUtils.isEmpty(contactList))	{
			throw new InvalidInputException("No contacts under account: " + accountId);
		}
		contactList.forEach(entity -> entityToDto(entity));
		return contactDtoList;
	}

	@Override
	public List<ContactDto> findContactsByOwnerId(Long ownerId) {
		List<ContactDto> contactDtoList = new ArrayList<>();
		List<Contact> contactList = contactRepository.findByContactOwner_UserId(ownerId);
		if(CollectionUtils.isEmpty(contactList))	{
			throw new InvalidInputException("No contacts under user: " + ownerId);
		}
		return contactDtoList;
	}
	
	private Contact dtoToEntity(ContactDto dto)	{
		Contact entity = new Contact();
		entity.setAccount(accountRepository.findByAccountId(dto.getAccountId()));
		entity.setContactOwner(appUserRepository.findByUserId(dto.getOwnerId()));
		entity.setDateOfBirth(dto.getDateOfBirth());
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(entity.getLastName());
		entity.setLeadSource(LeadSourceEnum.valueOf(dto.getLeadSource()));
		entity.setMobile(dto.getMobile());
		entity.setPhone(dto.getPhone());
		entity.setSalutation(SalutationEnum.valueOf(dto.getSalutation()));
		entity.setTitle(dto.getTitle());
		entity.setDepartment(dto.getDepartment());
		entity.setFax(dto.getFax());
		entity.setDescription(dto.getDescription());
		entity.setMailingAddress(addressService.dtoToEntity(dto.getMailingAddress()));
		entity.setOtherAddress(addressService.dtoToEntity(dto.getOtherAddress()));
		entity.setCreatedTime(LocalDateTime.now());
		entity.setModifiedTime(LocalDateTime.now());
		return entity;
	}

	
	private ContactDto entityToDto(Contact entity)	{
		ContactDto dto = new ContactDto();
		dto.setContactId(entity.getContactId());
		dto.setDateOfBirth(entity.getDateOfBirth());
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setLeadSource(entity.getLeadSource().name());
		dto.setMobile(entity.getMobile());
		dto.setPhone(entity.getPhone());
		dto.setSalutation(entity.getSalutation().name());
		return dto;
	}

	@Override
	public Map<String, List<String>> populateContactPgDdValues() {
		Map<String, List<String>> dropdowns = new HashMap<>();
		dropdowns.put("leadSources", Stream.of(LeadSourceEnum.values()).map(indType 
				-> indType.name()).collect(Collectors.toList()));
		return dropdowns;
	}

	@Override
	public boolean doesEmailIdExists(String email) {
		return contactRepository.findByEmail(email) != null ? true : false;
	}
}
