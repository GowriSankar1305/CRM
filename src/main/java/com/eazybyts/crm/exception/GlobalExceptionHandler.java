package com.eazybyts.crm.exception;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eazybyts.crm.dto.ApiResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = InvalidInputException.class)
	public ResponseEntity<?> handleInvalidInputException(InvalidInputException e) {
		log.error("input error {}", e);
		if (!CollectionUtils.isEmpty(e.getErrors())) {
			return new ResponseEntity<>(new ApiResponseDto(null, 400, e.getErrors()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new ApiResponseDto(e.getMessage(), 400, null), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = RuntimeException.class)
	public ApiResponseDto handleRuntimeException(RuntimeException re) {
		log.error("runtime error {}", re);
		return new ApiResponseDto("Unable to process your request. ", 500, null);
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ApiResponseDto handleRuntimeException(Exception e) {
		log.error("system error {}", e);
		return new ApiResponseDto("Some System error!", 500, null);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ApiResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException me) {
		log.error("validation failed-----> {}", me);
		return new ApiResponseDto(null, 400,
				me.getFieldErrors().stream().map(mapToErrorMsg).collect(Collectors.toList()));
	}

	private Function<FieldError, String> mapToErrorMsg = (error) -> {
		return error.getField() + " " + error.getDefaultMessage();
	};
}
