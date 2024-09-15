package com.eazybyts.crm.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.eazybyts.crm.enums.TaskStatusEnum;
import com.eazybyts.crm.enums.TaskSubjectEnum;
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
@Table(name = "tbl_task",schema = CrmConstants.SCHEMA)
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "taskIdGen")
	@SequenceGenerator(name = "taskIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_task")
	private Long taskId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private AppUser taskOwner;
	@Enumerated(EnumType.STRING)
	private TaskSubjectEnum taskSubject;
	private LocalDate dueDate;
	@Enumerated(EnumType.STRING)
	private TaskStatusEnum taskStatus;
	private String description;
	private Boolean isTaskReminderEnabled;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private Contact contact;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
}
