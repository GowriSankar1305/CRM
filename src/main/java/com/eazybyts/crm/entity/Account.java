package com.eazybyts.crm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.eazybyts.crm.enums.AccountTypeEnum;
import com.eazybyts.crm.enums.IndustryTypeEnum;
import com.eazybyts.crm.enums.OwnershipTypeEnum;
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
@Table(name = "tbl_account",schema = CrmConstants.SCHEMA)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "accIdGen")
	@SequenceGenerator(name = "accIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_account")
	private Long accountId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private AppUser accountOwner;
	private String website;
	private String fax;
	private String accountName;
	private String accountNumber;
	@OneToMany(mappedBy = "account",fetch = FetchType.LAZY)
	private List<Contact> contacts;
	@Enumerated(EnumType.STRING)
	private IndustryTypeEnum industry;
	@Enumerated(EnumType.STRING)
	private AccountTypeEnum accountType;
	private String phone;
	private BigDecimal annualRevenue;
	@Enumerated(EnumType.STRING)
	private OwnershipTypeEnum ownerShipType;
	private Integer employees;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_address_id")
	private Address shippingAddress;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "billing_address_id")
	private Address billingAddress;
	private String description;
	private String sicCode;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	private Long createdBy;
}
