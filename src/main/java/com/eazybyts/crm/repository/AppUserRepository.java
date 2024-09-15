package com.eazybyts.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eazybyts.crm.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	
	AppUser findByUserId(Long userId);
	
	@Query("SELECT au.userId,au.emailId,au.firstName,au.lastName FROM AppUser au where "
			+ "au.parentId = :id AND (au.firstName LIKE %:fname% or au.lastName LIKE %:lname%)")
	List<Object[]> findAllUsersByNameAndParentId(String fname,String lname,Long id);
	
	AppUser findByEmailId(String emailId);
	
	@Query("SELECT au.userId,au.emailId,au.firstName,au.lastName,au.mobileNo FROM AppUser au where "
			+ "au.parentId = :id")
	List<Object[]> findAllUsersByParentId(Long id);
}
