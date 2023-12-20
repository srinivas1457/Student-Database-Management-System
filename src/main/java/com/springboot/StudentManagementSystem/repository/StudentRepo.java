package com.springboot.StudentManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.springboot.StudentManagementSystem.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer>{

	public Student findByStudentEmail(String email);
	public Student findByStudentPhNo(long phno);
	
	@Query("select s.studentEmail from Student s where s.studentGrade=?1")
	public List<String> getAllEmailByGrade(String grade);
	
}
