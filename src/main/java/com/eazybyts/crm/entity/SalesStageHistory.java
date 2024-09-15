package com.eazybyts.crm.entity;

import java.time.LocalDateTime;

import com.eazybyts.crm.enums.SalesStageEnum;
import com.eazybyts.crm.util.CrmConstants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_sales_stage_history",schema = CrmConstants.SCHEMA)
public class SalesStageHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stageHistoryIdGen")
	@SequenceGenerator(name = "stageHistoryIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_sales_state_history")
	private Long historyId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "sales_id")
	private Sales sales;
	@Enumerated(EnumType.STRING)
	private SalesStageEnum salesStage;
	private String description;
	private LocalDateTime createdTime;
	private Long createdBy;
}
