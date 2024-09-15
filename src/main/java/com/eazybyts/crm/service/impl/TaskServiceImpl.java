package com.eazybyts.crm.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.DateDto;
import com.eazybyts.crm.dto.TaskDto;
import com.eazybyts.crm.entity.Task;
import com.eazybyts.crm.enums.TaskStatusEnum;
import com.eazybyts.crm.enums.TaskSubjectEnum;
import com.eazybyts.crm.exception.InvalidInputException;
import com.eazybyts.crm.repository.AppUserRepository;
import com.eazybyts.crm.repository.ContactRepository;
import com.eazybyts.crm.repository.TaskRepository;
import com.eazybyts.crm.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Override
	public ApiResponseDto addTask(TaskDto taskDto) {
		taskRepository.save(dtoToEntity(taskDto));
		return new ApiResponseDto("Task added successfully!", 201,null);
	}

	@Override
	public List<TaskDto> findTasksOfAssignee(Long id) {
		List<TaskDto> dtoList = new ArrayList<>();
		List<Task> taskList = taskRepository.findByContact_ContactId(id);
		if(!CollectionUtils.isEmpty(taskList))	{
			taskList.forEach(task -> dtoList.add(entityToDto(task)));
		}
		return dtoList;
	}

	@Override
	public List<TaskDto> findTasksByOwner(Long id) {
		List<TaskDto> dtoList = new ArrayList<>();
		List<Task> entityList = taskRepository.findByTaskOwner_UserId(id);
		if(!CollectionUtils.isEmpty(entityList))	{
			entityList.forEach(entity -> entityToDto(entity));
		}
		return dtoList;
	}

	@Override
	public TaskDto findTask(Long taskId) {
		Task task = taskRepository.findByTaskId(taskId);
		if(Objects.isNull(task))	{
			throw new InvalidInputException("No task found with id: " + taskId);
		}
		return entityToDto(task);
	}

	private Task dtoToEntity(TaskDto dto)	{
		Task task = new Task();
		task.setCreatedTime(LocalDateTime.now());
		task.setModifiedTime(LocalDateTime.now());
		task.setContact(contactRepository.findByContactId(dto.getContactId()));
		task.setDescription(dto.getDescription());
		DateDto date = dto.getDueDate();
		task.setDueDate(LocalDate.of(date.getYear(), date.getMonth(), date.getDate()));
		task.setIsTaskReminderEnabled(dto.getIsTaskReminderEnabled());
		task.setTaskOwner(appUserRepository.findByUserId(dto.getTaskOwnerId()));
		task.setTaskStatus(TaskStatusEnum.valueOf(dto.getTaskStatus()));
		task.setTaskSubject(TaskSubjectEnum.valueOf(dto.getTaskSubject()));
		return task;
	}
	
	private TaskDto entityToDto(Task entity)	{
		TaskDto dto = new TaskDto();
		if(Objects.isNull(entity))	{
			log.info("----- task entity is null -----");
		}
		dto.setContactId(entity.getContact().getContactId());
		dto.setDescription(entity.getDescription());
		LocalDate date = entity.getDueDate();
		dto.setDueDate(new DateDto(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
		dto.setIsTaskReminderEnabled(entity.getIsTaskReminderEnabled());
		dto.setTaskId(entity.getTaskId());
		dto.setTaskStatus(entity.getTaskStatus().name());
		dto.setTaskSubject(entity.getTaskSubject().name());
		return dto;
	}
}
