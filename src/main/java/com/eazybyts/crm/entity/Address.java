package com.eazybyts.crm.entity;

import com.eazybyts.crm.util.CrmConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_address",schema = CrmConstants.SCHEMA)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "aadrsIdGen")
	@SequenceGenerator(name = "aadrsIdGen",allocationSize = 1,initialValue = 
	CrmConstants.NO,schema = CrmConstants.SCHEMA,sequenceName = "seq_address")
	private Long addressId;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String country;
}
