package com.eazybyts.crm.dto;

import lombok.Data;

@Data
public class VendorDto {

	private Long vendorId;
	private Long ownerId;
	private String vendorName;
	private String phone;
	private String webSite;
	private String email;
	private String category;
	private String description;
	private String glAccount;
	private String leadSource;
	private AddressDto address;
}
