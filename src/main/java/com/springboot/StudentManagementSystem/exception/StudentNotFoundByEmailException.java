package com.springboot.StudentManagementSystem.exception;

public class StudentNotFoundByEmailException extends RuntimeException{
	
	String message ;
	
	public StudentNotFoundByEmailException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
