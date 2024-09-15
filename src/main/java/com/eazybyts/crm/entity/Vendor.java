package com.eazybyts.crm.entity;

import java.time.LocalDateTime;

import com.eazybyts.crm.enums.GeneralLedgerEnum;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_vendor",schema = CrmConstants.SCHEMA)
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vendorIdGen")
	@SequenceGenerator(name = "vendorIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_vendor")
	private Long vendorId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private AppUser vendorOwner;
	private String vendorName;
	private String phone;
	private String webSite;
	private String email;
	private String category;
	@Enumerated(EnumType.STRING)
	private LeadSourceEnum leadSource;
	@Enumerated(EnumType.STRING)
	private GeneralLedgerEnum generalLedgerAccount;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;
	private String description;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	private Long createdBy;
	private Long modifiedBy;
}
