package com.springboot.StudentManagementSystem.serviceimpl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.StudentManagementSystem.dto.MessageData;
import com.springboot.StudentManagementSystem.dto.StudentRequest;
import com.springboot.StudentManagementSystem.dto.StudentResponse;
import com.springboot.StudentManagementSystem.entity.Student;
import com.springboot.StudentManagementSystem.exception.StudentEmailNotFoundByGradeException;
import com.springboot.StudentManagementSystem.exception.StudentNotFoundByEmailException;
import com.springboot.StudentManagementSystem.exception.StudentNotFoundByIdException;
import com.springboot.StudentManagementSystem.exception.StudentNotFoundByPhoneNumberException;
import com.springboot.StudentManagementSystem.repository.StudentRepo;
import com.springboot.StudentManagementSystem.service.StudentService;
import com.springboot.StudentManagementSystem.util.ResponseStructure;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepo repo;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(StudentRequest studentRequest) {
		
/** Getting data from the request :*/
		Student student = new Student();
		student.setStudentName(studentRequest.getStudentName());
		student.setStudentEmail(studentRequest.getStudentEmail());
		student.setStudentGrade(studentRequest.getStudentGrade());
		student.setStudentPhNo(studentRequest.getStudentPhNo());
		student.setStudentPassword(studentRequest.getStudentPassword());
	
/** Sending the data to Student / Entity to be saved :*/		
		Student student2 = repo.save(student); 
		
/** Setting data to be the response **/
		StudentResponse response = new StudentResponse();
		response.setStudentId(student2.getStudentId());
		response.setStudentName(student2.getStudentName());
		response.setStudentGrade(student2.getStudentGrade());
		
		ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
		structure.setStatusCode(HttpStatus.CREATED.value());
		structure.setMessage("Student Data Inserted Successfully !");
		structure.setData(response);
		
		return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(StudentRequest studentRequest, int studentId) {
		
		Optional<Student> optional = repo.findById(studentId);
		 if(optional.isPresent()){
			 Student student = optional.get();
			 		 
			 student.setStudentId(student.getStudentId());
			 
			 student.setStudentName(studentRequest.getStudentName());
			 student.setStudentEmail(studentRequest.getStudentEmail());
			 student.setStudentPhNo(studentRequest.getStudentPhNo());
			 student.setStudentGrade(studentRequest.getStudentGrade());
			 student.setStudentPassword(studentRequest.getStudentPassword());
			 
			 Student student2 = repo.save(student);
			 
			 StudentResponse response = new StudentResponse();
			 response.setStudentId(student2.getStudentId());
			 response.setStudentName(student2.getStudentName());
			 response.setStudentGrade(student2.getStudentGrade());
			 
			 ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			 structure.setStatusCode(HttpStatus.OK.value());
			 structure.setMessage("Student Data Updated Successfully !");
			 structure.setData(response);
			 
			 return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.OK);
		 }else{
			 throw new StudentNotFoundByIdException("ID NotFound to Update the Data !!");
		 }
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(int studentId) {
		Optional<Student> optional = repo.findById(studentId);
		if(optional.isPresent()) {
			Student student = optional.get();
			repo.delete(student);
			
			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());
			
			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			 structure.setStatusCode(HttpStatus.OK.value());
			 structure.setMessage("Student Data Deleted Successfully !");
			 structure.setData(response);
			
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.OK);
		}else {
			throw new StudentNotFoundByIdException("ID NotFound to Delete the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findStudent(int studentId) {
		Optional<Student> optional = repo.findById(studentId);
		if(optional.isPresent()) {
			Student student = optional.get();
			
			StudentResponse response = new StudentResponse();
			 response.setStudentId(student.getStudentId());
			 response.setStudentName(student.getStudentName());
			 response.setStudentGrade(student.getStudentGrade());
			
			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Student Data Found Successfully !");
			structure.setData(response);
			
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.FOUND);
		}else {
			throw new StudentNotFoundByIdException("ID NotFound to Fetch the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findAllStudents() {
		List<Student> studentList = repo.findAll();
		if(!studentList.isEmpty()) {
			
			List<StudentResponse> responseList = new ArrayList<StudentResponse>();
			
			for(Student student : studentList) {
				StudentResponse response = new StudentResponse();
				response.setStudentId(student.getStudentId());
				response.setStudentName(student.getStudentName());
				response.setStudentGrade(student.getStudentGrade());
				responseList.add(response);
			}
			
			ResponseStructure<List<StudentResponse>> structure = 
										new ResponseStructure<List<StudentResponse>>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Student Records Found :");
			structure.setData(responseList);
			
			return new ResponseEntity<ResponseStructure<List<StudentResponse>>>(structure, HttpStatus.FOUND);
		}else {
			return null;
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findByEmail(String studentEmail) {
		Student student = repo.findByStudentEmail(studentEmail);
		if(student != null) {
			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());
			
			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Student Data Found by E-Mail Id.. !");
			structure.setData(response);
			
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.FOUND);
		}
		else {
				throw new StudentNotFoundByEmailException("Failed to fetch the Student Record");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findByPhNo(long phno) {
		Student student = repo.findByStudentPhNo(phno);
		if(student != null){
			
			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());
			
			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Student Data Found by Phone Number");
			structure.setData(response);
			
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.FOUND);	
		}else {
			throw new StudentNotFoundByPhoneNumberException("Failed to fetch the Student Record");
		}
		
	}

	@Override
	public ResponseEntity<ResponseStructure<List<String>>> fetchAllEmailByGrade(String grade) {
	  List<String> listEmail = repo.getAllEmailByGrade(grade);
	  System.out.println(listEmail);
	  
	  	if(!listEmail.isEmpty()) {
			ResponseStructure<List<String>> structure = new ResponseStructure<List<String>>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Student Email Found by Grade !!");
			structure.setData(listEmail);
			
			return new ResponseEntity<ResponseStructure<List<String>>>(structure , HttpStatus.FOUND);
	  }else {
		  throw new StudentEmailNotFoundByGradeException("Email Not found for the Requested Grade !!");
	  }
		
	}

	@Override
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file) throws IOException{
		/**
		org.apache.poi is a Java library that provides support for working with 
		Microsoft Office file formats, such as Excel, Word, and PowerPoint. 
		It stands for "Poor Obfuscation Implementation" or "P(ure) O(bject) I(nterface)," 
		and it's commonly referred to as Apache POI.
		**/
		
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		for(Sheet sheet : workbook) {
			for(Row row : sheet) {
				if(row.getRowNum() >0) {
					if(row != null) {
						String name = row.getCell(0).getStringCellValue();
						String email = row.getCell(1).getStringCellValue();
						long phoneNumber = (long)row.getCell(2).getNumericCellValue();
						
		/** Here, getNumericCellValue method's return type is double, so we have to downcast to long */
						
						String grade = row.getCell(3).getStringCellValue();
						String password = row.getCell(4).getStringCellValue();
						
						System.out.println("Name : "+name+
										 "\nEmail : "+email+
										 "\nPhone Number : "+phoneNumber+
										 "\nGrade : "+grade+
										 "\nPassword : "+password+"\n");
						
						Student student = new Student();
						student.setStudentName(name);
						student.setStudentEmail(email);
						student.setStudentPhNo(phoneNumber);
						student.setStudentGrade(grade);
						student.setStudentPassword(password);
						
						repo.save(student);
					}
				}
			}
		}
		workbook.close();
		return null;
	}

	@Override
	public ResponseEntity<String> writeToExcel(String filePath) throws IOException {
		
//		filePath should be with url encoder 
//		
//		: --> %3A
//		/ --> %2F
//		A normal space is url encoded as %20
		
		List<Student> studentList = repo.findAll();
		XSSFWorkbook workbook = new XSSFWorkbook();		
		XSSFSheet sheet = workbook.createSheet();
		
		Row header = sheet.createRow(0); // First Row's Index is 0
		/** Setting heading name for each field : **/
		header.createCell(0).setCellValue("StudentId"); 
		header.createCell(1).setCellValue("StudentName");
		header.createCell(2).setCellValue("StudentEmail");
		header.createCell(3).setCellValue("StudentPhNo");
		header.createCell(4).setCellValue("StudentGrade");
		header.createCell(5).setCellValue("StudentPassword");
		
		int rowNum = 1;
		for(Student student : studentList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(student.getStudentId());
			row.createCell(1).setCellValue(student.getStudentName());
			row.createCell(2).setCellValue(student.getStudentEmail());
			row.createCell(3).setCellValue(student.getStudentPhNo());
			row.createCell(4).setCellValue(student.getStudentGrade());
			row.createCell(5).setCellValue(student.getStudentPassword());	
		}
		FileOutputStream outputStream = new FileOutputStream(filePath);
		workbook.write(outputStream);
		
		workbook.close();
		return new ResponseEntity<String>("Data Transferred Successfully to the EXCEL Sheet !", HttpStatus.OK);
	}

	
	  @Override 
	  public ResponseEntity<String> extractDataFromCSV(MultipartFile file) throws IOException { 
		  BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8")); 
		  CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT); //
//		  					.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()); 
		  for(CSVRecord record : csvParser) {
	  
			  String name = record.get(0); 
			  String email = record.get(1); 
			  long phoneNumber = Long.parseLong(record.get(2)); 
			  String grade = record.get(3); 
			  String password = record.get(4);
	  
	  // System.out.println(name +" -- "+email+" -- "+phoneNumber+" -- "+grade+" -- "+password);
	  
			  Student student = new Student(); student.setStudentName(name);
			  student.setStudentEmail(email); student.setStudentPhNo(phoneNumber);
			  student.setStudentGrade(grade); student.setStudentPassword(password);
			  
			  repo.save(student); 
		  	} 
		  csvParser.close(); 
		  return new ResponseEntity<String>("CSV Data inserted into the Table",HttpStatus.CREATED);
		}
	  
//	@Override
//	public ResponseEntity<ResponseStructure<List<StudentResponse>>> extractDataFromCSV(MultipartFile file) throws IOException {	
//		BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
//		CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
//		
//		/*
//		 * It sets the first row as the header row. It makes the parsing of column
//		 * headers case-insensitive. It trims whitespace from the beginning and end of
//		 * each field value when parsing the CSV data.
//		 */
//		
//		List<Student> students = new ArrayList<Student>();
//		
//		Iterable<CSVRecord> csvRecords = csvParser.getRecords();
//		
//		List<StudentResponse> responseList = new ArrayList<StudentResponse>();
//		
//		for(CSVRecord record :csvRecords) {
//			String name = record.get("studentName");
//			String email = record.get("studentEmail");
//			Long phone = Long.parseLong(record.get("studentPhNo"));
//			String grade = record.get("studentGrade");
//			String password = record.get("studentPassword");
//			
//			System.out.println(name +" "+phone+" "+email+" "+password+" "+" "+grade);
//
//			Student student = new Student();
//			student.setStudentName(name);
//			student.setStudentEmail(email);
//			student.setStudentPhNo(phone);
//			student.setStudentPassword(password);
//			student.setStudentGrade(grade);
//			
//			student = repo.save(student);
//			
//			StudentResponse response = new StudentResponse();
//			response.setStudentId(student.getStudentId());
//			response.setStudentName(student.getStudentName());
//			response.setStudentGrade(student.getStudentGrade());
//			
//			responseList.add(response);	
//		}
//		ResponseStructure<List<StudentResponse>> structure = new ResponseStructure<List<StudentResponse>>();
//		structure.setStatusCode(HttpStatus.CREATED.value());
//		structure.setMessage("Data Saved Successfully");
//		structure.setData(responseList);
//		
//		csvParser.close();
//		fileReader.close();
//		return new ResponseEntity<ResponseStructure<List<StudentResponse>>>(structure , HttpStatus.CREATED);
//	}
	
	@Override
	public ResponseEntity<String> writeToCSV(String filePath) throws IOException {
//		CSVPrinter printer = new CSVPrinter(filePath, CSVFormat.DEFAULT);
		return null;
	}

	@Override
	public ResponseEntity<String> sendMail(MessageData messageData) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(messageData.getTo());
		message.setSubject(messageData.getSubject());
		message.setText(messageData.getText()
						+"\n\nThanks & Regards"
						+"\n"+messageData.getSenderName()
						+"\n"+messageData.getSenderAddress()
				);
		message.setSentDate(new Date());
		
		javaMailSender.send(message);
		
		return new ResponseEntity<String>("Mail Send Successfully !!" , HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> sendMimeMessage(MessageData messageData) throws MessagingException{
		
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mime, true);
		
/***  MimeMessageHelper is a class has a constructor to get MimeMessage Object as an argument and 
	  Boolean value.Needs to mention whether we are having multipart file or not (Like doc,image,video). 
 	  If you are passing make it TRUE,it will throw the MessagingException
**/
		message.setTo(messageData.getTo());
		message.setSubject(messageData.getSubject());
		
		/* FOR ONLY TEXT :
		 * ----------------
		 * String emailBody = messageData.getText() +"<br><h4>Thanks & Regards</h4>"
		 * 											+"<h4>"+ messageData.getSenderName()+"</h4>"
		 * 											+"<h4>"+messageData.getSenderAddress()+"</h4>";
		 */
		
		String emailBody = messageData.getText() +"<br><h4>Thanks & Regards</h4>"
				 								 +"<h4>"+ messageData.getSenderName()+"</h4>" 
				 								 +"<h4>"+messageData.getSenderAddress()+"</h4>"
				 								 +"<img src=\"https://www.google.com/logos/doodles/2023/temp-celebrating-chandrayaan-3-6753651837110163-s.png\" width=\"200\">";
		
		/** if it is true then it will understand that your message consists HTML Document as well
		 *  else(FALSE) it will understood that its just a Text Document.
		 *  
		 *  If we mention TRUE and not having HTML Document,it will throw the MessagingException. 
		 * */
		
		message.setText(emailBody , true); 
		message.setSentDate(new Date());
		
		javaMailSender.send(mime);

		return new ResponseEntity<String>("Mime Message sent Successfully !!" , HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> isMailPresent(String mail) {
		Student student = repo.findByStudentEmail(mail);
		
		if(student == null) {
			
		}
		
		return null;
	}



}
