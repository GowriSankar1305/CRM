package com.eazybyts.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eazybyts.crm.entity.Account;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByAccountId(Long accountId);
	List<Account> findByAccountOwner_UserId(Long userId);
	Account findByAccountName(String accountName);
	@Query("SELECT ac.accountName,ac.phone,ac.website,ac.accountNumber FROM Account ac WHERE ac.createdBy =:id")
	List<Object[]> findAllAccountsOfAdminUser(Long id);
}
