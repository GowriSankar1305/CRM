package com.eazybyts.crm.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5364036974728837197L;
	private List<String> errors;
	
	public InvalidInputException(String message) {
		super(message);
	}
	
	public InvalidInputException(List<String> errors)	{
		this.errors = errors;
	}
}
