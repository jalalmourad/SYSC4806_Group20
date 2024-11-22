package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sysc4806.sysc4806_group20.Model.Student;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
import org.sysc4806.sysc4806_group20.Service.PasswordService;
import org.sysc4806.sysc4806_group20.Service.StudentService;
import org.sysc4806.sysc4806_group20.Service.UserAccountService;
import java.util.Map;
import static org.sysc4806.sysc4806_group20.Model.UserRole.STUDENT;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/newStudent")
    public ResponseEntity<?> newStudent(@RequestParam(value = "firstName") String firstName,
                                  @RequestParam(value = "lastName") String lastName,
                                @RequestParam(value = "studentNumber") Long studentId,
                              @RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String hashPassword){
        // Validate inputs
        if (firstName == null || lastName == null || studentId == null || username == null || hashPassword == null ||
                firstName.isBlank() || lastName.isBlank() || username.isBlank() || hashPassword.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "All fields are required."));
        }

        try {
            // Create and save Professor
            Student newStud = new Student(firstName, lastName, studentId);
            Student savedStudent = studentService.save(newStud);

            // Hash password
            String hashedPassword = passwordService.hashToSave(hashPassword);

            // Create and save UserAccount
            UserAccount userAccount = new UserAccount(username, hashedPassword, STUDENT);
            userAccount.setStudent(savedStudent);
            userAccountService.save(userAccount);

            // Respond with success marker
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Student created successfully.",
                    "professor", savedStudent // Include necessary details or use a DTO
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "An error occurred while creating the student."));
        }
    }
}
