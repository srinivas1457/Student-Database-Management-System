package com.springboot.StudentManagementSystem.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.StudentManagementSystem.util.ErrorStructure;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	/*
	@ExceptionHandler
	public ResponseEntity<String> studentNotFoundById(StudentNotFoundByIdException exp)
	{
		return new ResponseEntity<String>(exp.getMessage(), HttpStatus.NOT_FOUND);
	}
	*/
	
	/** After extending 'ResponseEntityExceptionHandler' class, 
	 	type handleMethodArgumentNotValid this will give you the Exception Implementation**/
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<ObjectError> allErrors = ex.getAllErrors(); // UPCASTING : ObjectError is the SuperClass of FieldError which extends Error Class.
		
		Map<String, String> mapErrors = new HashMap<String, String>();
		
		for(ObjectError error : allErrors) {
			FieldError fieldError = (FieldError)error;  // DOWNCASTING : ObjectError -> FieldError
			String message = fieldError.getDefaultMessage(); /** VALUE **/
			String field = fieldError.getField(); /** KEY **/
			
			mapErrors.put(field,message);
		}
		
		return new ResponseEntity<Object>(mapErrors, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> studentNotFoundById(StudentNotFoundByIdException exp)
	{
		ErrorStructure structure = new ErrorStructure();
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setMessage(exp.getMessage());
		structure.setRootCause("Student record not found for the requested ID !!");
		return new ResponseEntity<ErrorStructure>(structure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> studentNotFoundByEmail(StudentNotFoundByEmailException exp)
	{
		ErrorStructure structure = new ErrorStructure();
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setMessage(exp.getMessage());
		structure.setRootCause("Student record not found for the requested Email !!");
		return new ResponseEntity<ErrorStructure>(structure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> studentNotFoundByPhoneNumber(StudentNotFoundByPhoneNumberException exp)
	{
		ErrorStructure structure = new ErrorStructure();
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setMessage(exp.getMessage());
		structure.setRootCause("Student record not found for the requested Phone Number !!");
		return new ResponseEntity<ErrorStructure>(structure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> studentNotFoundByGrade(StudentEmailNotFoundByGradeException exp)
	{
		ErrorStructure structure = new ErrorStructure();
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setMessage(exp.getMessage());
		structure.setRootCause("Student record not found for the requested GRADE !!");
		return new ResponseEntity<ErrorStructure>(structure, HttpStatus.NOT_FOUND);
	}
	
}
