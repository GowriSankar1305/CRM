package com.eazybyts.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybyts.crm.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
