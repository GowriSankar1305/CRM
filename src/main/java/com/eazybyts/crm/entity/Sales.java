package com.eazybyts.crm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.eazybyts.crm.enums.LeadSourceEnum;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_sales",schema = CrmConstants.SCHEMA)
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "salesIdGen")
	@SequenceGenerator(name = "salesIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_sales")
	private Long saleId;
	private String saleName;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;
	private BigDecimal amount;
	@Enumerated(EnumType.STRING)
	private LeadSourceEnum LeadSource;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private Contact contact;
	private LocalDate closingDate;
	private Integer probability;
	private String description;
	@OneToMany(mappedBy = "sales",fetch = FetchType.LAZY)
	private List<SalesStageHistory> stageHistory;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private AppUser createdBy;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	private AppUser modifiedBy;
}
