package com.eazybyts.crm.dto;

import lombok.Data;

@Data
public class SalesStageHistoryDto {
	private Long historyId;
	private String salesStage;
	private Long salesId;
	private String description;
	private String createdTime;
	private String createdBy;
}
