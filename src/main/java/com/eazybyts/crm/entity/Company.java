package com.eazybyts.crm.entity;

import java.time.LocalDateTime;

import com.eazybyts.crm.util.CrmConstants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "tbl_company",schema = CrmConstants.SCHEMA)
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "companyIdGen")
	@SequenceGenerator(name = "companyIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_company")
	private Long companyId;
	private String companyName;
	private String companyAlias;
	private Long noOfEmployees;
	private String phoneNo;
	private String mobileNo;
	private String fax;
	private String websiteUrl;
	private String description;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private AppUser owner;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
}
