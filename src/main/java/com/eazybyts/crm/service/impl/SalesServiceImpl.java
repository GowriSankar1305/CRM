package com.eazybyts.crm.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.DateDto;
import com.eazybyts.crm.dto.SalesDto;
import com.eazybyts.crm.dto.SalesStageHistoryDto;
import com.eazybyts.crm.entity.Sales;
import com.eazybyts.crm.entity.SalesStageHistory;
import com.eazybyts.crm.enums.LeadSourceEnum;
import com.eazybyts.crm.enums.SalesStageEnum;
import com.eazybyts.crm.exception.InvalidInputException;
import com.eazybyts.crm.repository.AccountRepository;
import com.eazybyts.crm.repository.ContactRepository;
import com.eazybyts.crm.repository.SalesRepository;
import com.eazybyts.crm.repository.SalesStageHistoryRepository;
import com.eazybyts.crm.service.SalesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	private SalesRepository salesRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private SalesStageHistoryRepository stageHistoryRepository;
	
	@Override
	@Transactional
	public ApiResponseDto addSales(SalesDto salesDto) {
		Sales newSales = salesRepository.save(dtoToEntity(salesDto));
		SalesStageHistoryDto stageHistoryDto = salesDto.getStageHistory().get(0);
		SalesStageHistory stageHistory = new SalesStageHistory();
		stageHistory.setSalesStage(SalesStageEnum.valueOf(stageHistoryDto.getSalesStage()));
		stageHistory.setDescription(stageHistoryDto.getDescription());
		stageHistory.setSales(newSales);
		stageHistory.setCreatedTime(LocalDateTime.now());
		stageHistoryRepository.save(stageHistory);
		return new ApiResponseDto("Sales added successfully!", 201,null);
	}

	@Override
	public SalesDto findSalesById(Long salesId) {
		Sales sales = salesRepository.findBySaleId(salesId);
		if(Objects.isNull(sales))	{
			throw new InvalidInputException("Sales not found with id:" + salesId);
		}
		return entityToDto(sales);
	}

	@Override
	public List<SalesDto> findSalesByAccountId(Long accId) {
		List<SalesDto> dtoList = new ArrayList<>();
		List<Sales> salesList = salesRepository.findByAccount_AccountId(accId);
		if(Objects.isNull(salesList))	{
			log.info("----No sales for account : " + accId);
		}
		return dtoList;
	}

	@Override
	public List<SalesDto> findSalesByContactId(Long contactId) {
		List<SalesDto> dtoList = new ArrayList<>();
		List<Sales> salesList = salesRepository.findByContact_ContactId(contactId);
		if(Objects.isNull(salesList))	{
			log.info("----No sales for contact : " + contactId);
		}
		return dtoList;
	}

	@Override
	public List<SalesDto> findSalesByUserId(Long userId) {
		List<SalesDto> dtoList = new ArrayList<>();
		List<Sales> salesList = salesRepository.findByCreatedBy_UserId(userId);
		if(Objects.isNull(salesList))	{
			log.info("----No sales for user: " + userId);
		}
		return dtoList;
	}
	
	private Sales dtoToEntity(SalesDto dto)	{
		Sales entity = new Sales();
		entity.setAccount(accountRepository.findByAccountId(dto.getAccountId()));
		entity.setContact(contactRepository.findByContactId(dto.getContactId()));
		entity.setAmount(dto.getAmount());
		DateDto date = dto.getClosingDate();
		entity.setClosingDate(LocalDate.of(date.getYear(), date.getMonth(), date.getDate()));
		entity.setDescription(dto.getDescription());
		entity.setLeadSource(LeadSourceEnum.valueOf(dto.getLeadSource()));
		entity.setProbability(dto.getProbability());
		entity.setSaleName(dto.getSalesName());
		entity.setCreatedTime(LocalDateTime.now());
		entity.setModifiedTime(LocalDateTime.now());
		return entity;
	}
	
	private SalesDto entityToDto(Sales entity)	{
		SalesDto dto = new SalesDto();
		dto.setSalesId(entity.getSaleId());
		dto.setAmount(entity.getAmount());
		if(Objects.isNull(entity.getClosingDate()))	{
			LocalDate date = entity.getClosingDate();
			dto.setClosingDate(new DateDto(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));	
		}
		dto.setDescription(entity.getDescription());
		dto.setLeadSource(entity.getLeadSource().name());
		dto.setProbability(entity.getProbability());
		dto.setSalesId(entity.getSaleId());
		dto.setSalesName(entity.getSaleName());
		if(!CollectionUtils.isEmpty(entity.getStageHistory()))	{
			List<SalesStageHistoryDto> dtoList = new ArrayList<>();
			entity.getStageHistory().forEach(history -> {
				dtoList.add(entityToDto(history));
			});
			dto.setStageHistory(dtoList);
		}
		return dto;
	}
	
	public SalesStageHistoryDto entityToDto(SalesStageHistory entity) {
		SalesStageHistoryDto dto = new SalesStageHistoryDto();
		if(Objects.isNull(entity))	{
			log.info("----- stage history entity is null -----");
		}
		dto.setDescription(entity.getDescription());
		dto.setHistoryId(entity.getHistoryId());
		dto.setSalesStage(entity.getSalesStage().name());
		dto.setCreatedTime(entity.getCreatedTime().toString());
		return dto;
	}
}
