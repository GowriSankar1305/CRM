package com.eazybyts.crm.dto;

import lombok.Data;

@Data
public class ContactDto {
	private Long contactId;
	private Long ownerId;
	private String salutation;
	private String firstName;
	private String lastName;
	private String leadSource;
	private Long accountId;
	private String email;
	private String phone;
	private String mobile;
	private String dateOfBirth;
	private String title;
	private String department;
	private String fax;
	private String description;
	private AddressDto mailingAddress;
	private AddressDto otherAddress;
	private Long createdBy;
	private Long modifiedBy;
}
