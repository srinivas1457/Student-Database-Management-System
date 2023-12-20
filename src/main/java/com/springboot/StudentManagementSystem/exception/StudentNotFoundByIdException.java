package com.springboot.StudentManagementSystem.exception;

public class StudentNotFoundByIdException extends RuntimeException{

	String message;
	
	public StudentNotFoundByIdException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
