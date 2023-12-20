package com.springboot.StudentManagementSystem.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.StudentManagementSystem.dto.StudentRequest;
import com.springboot.StudentManagementSystem.dto.StudentResponse;
import com.springboot.StudentManagementSystem.dto.MessageData;
import com.springboot.StudentManagementSystem.util.ResponseStructure;

public interface StudentService {

	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(StudentRequest studentRequest);
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(StudentRequest studentRequest, int studentId);
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(int studentId);
	public ResponseEntity<ResponseStructure<StudentResponse>> findStudent(int studentId);
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findAllStudents();
	
	public ResponseEntity<ResponseStructure<StudentResponse>> findByEmail(String studentEmail);
	public ResponseEntity<ResponseStructure<StudentResponse>> findByPhNo(long phno);
	
	public ResponseEntity<ResponseStructure<List<String>>> fetchAllEmailByGrade(String grade);
	
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file) throws IOException;
	
	public ResponseEntity<String> writeToExcel(String filePath) throws IOException;
	
	public ResponseEntity<String> extractDataFromCSV(MultipartFile file) throws IOException;
	
//	public ResponseEntity<ResponseStructure<List<StudentResponse>>> extractDataFromCSV(MultipartFile file) throws IOException;
	
	public ResponseEntity<String> writeToCSV(String filePath) throws IOException;

	public ResponseEntity<String> sendMail(MessageData data);
	
	public ResponseEntity<String> sendMimeMessage(MessageData messageData) throws MessagingException;
	
	public ResponseEntity<String> isMailPresent(String mail);
}
