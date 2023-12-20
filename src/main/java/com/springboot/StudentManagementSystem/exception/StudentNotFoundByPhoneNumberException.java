package com.springboot.StudentManagementSystem.exception;

public class StudentNotFoundByPhoneNumberException extends RuntimeException{
	
	String message;
	
	public StudentNotFoundByPhoneNumberException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
}
