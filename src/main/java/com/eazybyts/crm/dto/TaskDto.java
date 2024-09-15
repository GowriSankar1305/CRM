package com.eazybyts.crm.dto;

import lombok.Data;

@Data
public class TaskDto {
	
	private Long taskId;
	private Long taskOwnerId;
	private String taskSubject;
	private DateDto dueDate;
	private String taskStatus;
	private String description;
	private Boolean isTaskReminderEnabled;
	private Long contactId;
}
