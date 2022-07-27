package com.project.securitycourse.student;

import com.project.securitycourse.security.ApplicationUserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.project.securitycourse.security.ApplicationUserRole.STUDENT;

@RestController
@RequestMapping("/management/api/v1.0/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Boris"),
            new Student(2, "Matthewza Gorou")
    );

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        System.out.println(STUDENT.name());
        return STUDENTS;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.printf("Added Student: %d! (or not, apparently...)", student.getId());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("id") int id) {
        System.out.printf("Deleted Student: %d (or did I... heheheheheh)", id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("id") int id,
                              @RequestBody Student student) {
        System.out.printf("Updated Student: %d with info from: %s", id, student.getId());
    }

}
