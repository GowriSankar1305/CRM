package com.eazybyts.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateDto {
	private int year;
	private int month;
	private int date;
}
