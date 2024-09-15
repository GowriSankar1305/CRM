package com.eazybyts.crm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto {
	private String message;
	private int statusCode;
	private List<String> errors;
}
