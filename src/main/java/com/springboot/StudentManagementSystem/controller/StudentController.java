package com.springboot.StudentManagementSystem.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.StudentManagementSystem.dto.MessageData;
import com.springboot.StudentManagementSystem.dto.StudentRequest;
import com.springboot.StudentManagementSystem.dto.StudentResponse;
import com.springboot.StudentManagementSystem.service.StudentService;
import com.springboot.StudentManagementSystem.util.ResponseStructure;

@RestController
@RequestMapping("/students") /** It is like a basePackage **/
public class StudentController {

	@Autowired
	private StudentService service;

	/**
	 * @RequestMapping(method = RequestMethod.POST, value="student") instead of the
	 *                 above line, we can DIRECTLY ANNOTATE WITH  ---> @PostMapping
	 *                       
	 **/

/**	@PostMapping("/student") --> Until we are having another same request, we don't need to mention
 								 the package with request. Here we are having only one Post Request **/
	
	@PostMapping
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(@RequestBody @Valid StudentRequest studentRequest) {
		return service.saveStudent(studentRequest);
	}
 
/**	@PutMapping("/student") **/
	
	@PutMapping
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(@RequestBody @Valid StudentRequest studentRequest, @RequestParam int studentId) {
		return service.updateStudent(studentRequest, studentId);
	}

/**	@DeleteMapping("/student") **/
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(@RequestParam int studentId){
		return service.deleteStudent(studentId);
	}
	
/**	@GetMapping("/student/{studentId}") **/
	
	@GetMapping("/{studentId}")
	public ResponseEntity<ResponseStructure<StudentResponse>> findStudent(@PathVariable int studentId){
		return service.findStudent(studentId);
	}
	
/**	@GetMapping("/student") **/
	@CrossOrigin
	@GetMapping
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findAllStudents(){
		return service.findAllStudents();
	}

	@GetMapping(params = "studentEmail") /* http://localhost:8080/students?studentEmail=tom@gmail.com */
	public ResponseEntity<ResponseStructure<StudentResponse>> findByEmail(@RequestParam String studentEmail) {
		return service.findByEmail(studentEmail);
	}

	@GetMapping(params = "phno")
	public ResponseEntity<ResponseStructure<StudentResponse>> findByPhNo(@RequestParam long phno){
		return service.findByPhNo(phno);
	}
	
	@GetMapping(params = "grade")
	public ResponseEntity<ResponseStructure<List<String>>> fetchAllEmailByGrade(@RequestParam String grade) {
		return service.fetchAllEmailByGrade(grade);
	}
	
	@PostMapping("/extract")
	public ResponseEntity<String> extractDataFromExcel(@RequestParam MultipartFile file) throws IOException{
		return service.extractDataFromExcel(file);
	}
	
	@PostMapping("/write/excel")
	public ResponseEntity<String> writeToExcel(@RequestParam String filePath) throws IOException{
		return service.writeToExcel(filePath);
	}
	
	@PostMapping("/extract/csv")
	public ResponseEntity<String> extractDataFromCSV(@RequestParam MultipartFile file) throws IOException {
		return service.extractDataFromCSV(file);
	}
	
//	@PostMapping("/extract/csv")
//	public ResponseEntity<ResponseStructure<List<StudentResponse>>> extractDataFromCSV(@RequestParam MultipartFile file) throws IOException {
//		return service.extractDataFromCSV(file);
//	}
	
	@PostMapping("/mail")
	public ResponseEntity<String> sendMail(@RequestBody MessageData messageData) {
		return service.sendMail(messageData);
	}
	
//	@PostMapping("/mail")
//	public ResponseEntity<String> sendMail(HttpServletRequest request,MessageData messageData) {
//		
//		String toAddress = request.getParameter("to");
//		String subject = request.getParameter("sub");
//		String text = request.getParameter("text");
//		String senderName = request.getParameter("senderName");
//		String senderAdd  = request.getParameter("senderAddress");
//		
//		System.out.println(toAddress + " " + subject +" "+ text +" "+" "+ senderName +" "+senderAdd);
//		
//		return service.sendMail(messageData);
//	}
	
	@CrossOrigin
	@PostMapping("/mime-message")
	public ResponseEntity<String> sendMimeMessage(@RequestBody MessageData messageData) throws MessagingException{
		return service.sendMimeMessage(messageData);
	}
}
