package com.eazybyts.crm.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class SalesDto {
	private Long salesId;
	private String salesName;
	private Long accountId;
	private BigDecimal amount;
	private String LeadSource;
	private Long contactId;
	private DateDto closingDate;
	private Integer probability;
	private String description;
	private List<SalesStageHistoryDto> stageHistory;
}
