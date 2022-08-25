package com.example.demo;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class StudentController {
	@Autowired
	StudentRepository studentrepository;
	
	// Fetching all items 
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false)String studentName){
		try {
			List<Student> students = new ArrayList<Student>();
			if(studentName == null) {
				studentrepository.findAll().forEach(students::add);
			}
			else {
				studentrepository.findBystudentNameContaining(studentName).forEach(students::add);
			}
			if (students.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				return new ResponseEntity<>(students, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
		}
	
	// Fetching single item
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
	Optional<Student> studentData = studentrepository.findById(id);
	if (studentData.isPresent()) {
	return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
	} else {
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	}
	
	// Creating new data
	@PostMapping("/studentpost")
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
	try {
	Student _student =  studentrepository
			.save(new Student(student.getStudentName(),student.getMailId(),student.getPhoneNumber(),student.getDepartment()));
	return new ResponseEntity<>(_student, HttpStatus.CREATED);
	} catch (Exception e) {
	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	// Updating details using id
	@PutMapping("/students/{studentId}")
	public ResponseEntity<Student> updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student) {
	Optional<Student> studentData = studentrepository.findById(studentId);
	if (studentData.isPresent()) {
		
	Student student1 = studentData.get();
	student1.setStudentName(student.getStudentName());
	student1.setMailId(student.getMailId());
	student1.setPhoneNumber(student.getPhoneNumber());
	student1.setDepartment(student.getDepartment());
	
	return new ResponseEntity<>(studentrepository.save(student), HttpStatus.OK);
	} else {
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	}
	
	// Deleting single item
	@DeleteMapping("/students/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") int id) {
	try {
	studentrepository.deleteById(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (Exception e) {
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	@DeleteMapping("/students")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
	try {
	studentrepository.deleteAll();
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (Exception e) {
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	

}

