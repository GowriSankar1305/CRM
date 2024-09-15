package com.eazybyts.crm.dto;

import java.util.List;

import lombok.Data;

@Data
public class AccountDto {
	private Long accountId;
	
	private Long ownerId;
	private String accountName;
	private String website;
	private String fax;
	private String accountNumber;
	private List<ContactDto> contacts;
	private String industry;
	private String accountType;
	private String phone;
	private String annualRevenue;
	private String ownerShipType;
	private Integer employees;
	private AddressDto shippingAddress;
	private AddressDto billingAddress;
	private String description;
	private String sicCode;
}
