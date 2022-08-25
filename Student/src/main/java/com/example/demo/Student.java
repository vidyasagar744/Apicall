package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "studentdetail")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "my_seq_gen")
	@SequenceGenerator(name = "my_seq_gen", sequenceName = "ENTITY_SEQ")
	int studentId;
	String studentName;
	String mailId;
	int phoneNumber;
	String department;
	
	
	public Student() {
		
	}
	
	// Constructor using parameters
	public Student(String name, String mailId, int phoneNumber, String department) {
		super();
		this.studentName = name;
		this.mailId = mailId;
		this.phoneNumber = phoneNumber;
		this.department = department;
	}
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
