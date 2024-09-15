package com.eazybyts.crm.entity;

import java.time.LocalDateTime;

import com.eazybyts.crm.enums.LeadSourceEnum;
import com.eazybyts.crm.enums.SalutationEnum;
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
@Table(name = "tbl_contact",schema = CrmConstants.SCHEMA)
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "contactIdGen")
	@SequenceGenerator(name = "contactIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_contact")
	private Long contactId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private AppUser contactOwner;
	@Enumerated(EnumType.STRING)
	private SalutationEnum salutation;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private LeadSourceEnum leadSource;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;
	private String email;
	private String phone;
	private String mobile;
	private String title;
	private String department;
	private String fax;
	private String dateOfBirth;
	private String description;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "mailing_address_id")
	private Address mailingAddress;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "other_address_id")
	private Address otherAddress;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	private Long createdBy;
	private Long modifiedBy;
}
