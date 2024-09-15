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

@Getter
@Setter
@Entity
@Table(schema = CrmConstants.SCHEMA,name = "tbl_user_group")
public class UserGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userGrpIdGen")
	@SequenceGenerator(name = "userGrpIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_user_group")
	private Long groupId;
	private String groupName;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;
	private String description;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
}
