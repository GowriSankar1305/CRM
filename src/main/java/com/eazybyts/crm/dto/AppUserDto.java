package com.eazybyts.crm.dto;

import com.eazybyts.crm.annotation.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AppUserDto {
	private Long userId;
	private String firstName;
	private String lastName;
	@JsonProperty("userFullName")
	private String fullName;
	@JsonProperty("userPassword")
	private String password;
	private String confirmPassword;
	//@UniqueEmail
	@JsonProperty("userEmail")
	private String emailId;
	private String alias;
	private String phoneNo;
	@JsonProperty("userMobile")
	private String mobileNo;
	private String websiteUrl;
	private String faxNo;
	private String dateOfBirth;
	private String userRole;
	private Long parentId;
	private AddressDto address;
}
