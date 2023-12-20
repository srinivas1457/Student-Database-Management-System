package com.springboot.StudentManagementSystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class StudentRequest {

	// this field is required but it also accepts Empty String 
	@NotNull(message = "Student Name field should not be NULL") 
	private String studentName;
	
	//this field is required and it will not accept Empty String
	@NotBlank(message = "Student Email field Should not be BLANK")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[g][m][a][i][l]+.[c][o][m]", 
	message = "invalid email--Should be in the extension of '@gmail.com' ")
	private String studentEmail;
	
	@Min(value = 6000000000l, message = "Phone Number should not start below '6' !!")
	@Max(value = 9999999999l, message = "Phone Number cannot be above 10 Digit !!")
	private long studentPhNo;
	
	private String studentGrade;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
			message = "8 characters mandatory(1 upperCase,1 lowerCase,1 special Character,1 number)")
	private String studentPassword;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public long getStudentPhNo() {
		return studentPhNo;
	}
	public void setStudentPhNo(long studentPhNo) {
		this.studentPhNo = studentPhNo;
	}
	public String getStudentGrade() {
		return studentGrade;
	}
	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}
	public String getStudentPassword() {
		return studentPassword;
	}
	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	
}
