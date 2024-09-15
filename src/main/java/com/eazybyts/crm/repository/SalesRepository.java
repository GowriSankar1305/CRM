package com.eazybyts.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybyts.crm.entity.Sales;
import java.util.List;


public interface SalesRepository extends JpaRepository<Sales, Long> {

	Sales findBySaleId(Long saleId);
	List<Sales> findByAccount_AccountId(Long accountId);
	List<Sales> findByContact_ContactId(Long contactId);
	List<Sales> findByCreatedBy_UserId(Long userId);
}
