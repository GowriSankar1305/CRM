package com.eazybyts.crm.service;

import java.util.List;

import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.TaskDto;

public interface TaskService {
	public ApiResponseDto addTask(TaskDto taskDto);
	public List<TaskDto> findTasksOfAssignee(Long id);
	public List<TaskDto> findTasksByOwner(Long id);
	public TaskDto findTask(Long taskId);
}
