package com.eazybyts.crm.entity;

import java.time.LocalDateTime;

import com.eazybyts.crm.enums.UserRoleEnum;
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
@Table(name = "tbl_app_user",schema = CrmConstants.SCHEMA)
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userIdGen")
	@SequenceGenerator(name = "userIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_app_user")
	private Long userId;
	private String firstName;
	private String lastName;
	private String fullName;
	private String emailId;
	private String password;
	private String alias;
	private String phoneNo;
	private String mobileNo;
	private String websiteUrl;
	private String faxNo;
	private String dateOfBirth;
	@Enumerated(EnumType.STRING)
	private UserRoleEnum userRole;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;
	private Long parentId; 
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
}
