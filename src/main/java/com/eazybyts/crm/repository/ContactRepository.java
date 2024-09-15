package com.eazybyts.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybyts.crm.entity.Contact;
import java.util.List;


public interface ContactRepository extends JpaRepository<Contact, Long> {

	Contact findByContactId(Long contactId);
	List<Contact> findByAccount_AccountId(Long accountId);
	List<Contact> findByContactOwner_UserId(Long userId);
	Contact findByEmail(String email);
}
