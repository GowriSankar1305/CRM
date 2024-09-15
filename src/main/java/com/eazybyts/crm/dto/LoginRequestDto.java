package com.eazybyts.crm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
	@NotBlank
	private String userName;
	@NotBlank
	private String userPassword;
}
