package com.eazybyts.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybyts.crm.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	List<Task> findByContact_ContactId(Long contactId);
	List<Task> findByTaskOwner_UserId(Long userId);
	Task findByTaskId(Long taskId);
}
