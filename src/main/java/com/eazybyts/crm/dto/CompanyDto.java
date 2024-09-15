package com.eazybyts.crm.dto;

import lombok.Data;

@Data
public class CompanyDto {
	private Long companyId;
	private String companyName;
	private String companyAlias;
	private Long noOfEmployees;
	private String phoneNo;
	private String mobileNo;
	private String fax;
	private String websiteUrl;
	private String description;
	private AddressDto address;
	private Long ownerId;
}
