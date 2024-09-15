package com.eazybyts.crm.dto;

import lombok.Data;

@Data
public class AddressDto {
	private Long addressId;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String country;
}
