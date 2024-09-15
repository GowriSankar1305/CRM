package com.eazybyts.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybyts.crm.entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

}
