package com.eazybyts.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybyts.crm.entity.Vendor;
import java.util.List;


public interface VendorRepository extends JpaRepository<Vendor, Long> {
	Vendor findByVendorId(Long vendorId);
	List<Vendor> findByVendorOwner_UserId(Long userId);
	List<Vendor> findByCreatedBy(Long createdBy);
	Vendor findByVendorName(String vendorName);
	Vendor findByEmail(String email);
}
