package com.eazybyts.crm.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
	private String token;
	private String userEmail;
	private Long userId;
}
