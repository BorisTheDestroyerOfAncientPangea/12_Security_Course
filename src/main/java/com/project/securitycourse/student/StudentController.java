package com.project.securitycourse.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Dawid Kwiecien"),
            new Student(2, "Matthewza Gorou")
    );

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") int id) {
        System.out.println(StudentController.STUDENTS.get(0).getId());
        return StudentController.STUDENTS.stream()
                .filter(student -> student.getId()==id)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student with ID: " + id + " not found"));
    }

}
