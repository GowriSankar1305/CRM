package com.eazybyts.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybyts.crm.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Company findByCompanyId(Long companyId);
}
