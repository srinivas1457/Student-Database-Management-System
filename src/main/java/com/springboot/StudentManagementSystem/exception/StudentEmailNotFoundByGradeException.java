package com.springboot.StudentManagementSystem.exception;

public class StudentEmailNotFoundByGradeException extends RuntimeException{

	String message;
	
	public StudentEmailNotFoundByGradeException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
