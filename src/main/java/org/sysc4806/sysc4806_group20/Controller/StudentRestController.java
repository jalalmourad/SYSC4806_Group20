package org.sysc4806.sysc4806_group20.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.sysc4806.sysc4806_group20.Model.Student;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
import org.sysc4806.sysc4806_group20.Service.PasswordService;
import org.sysc4806.sysc4806_group20.Service.StudentService;
import org.sysc4806.sysc4806_group20.Service.UserAccountService;

import java.util.HashMap;
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
                    "student", savedStudent // Include necessary details or use a DTO
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "An error occurred while creating the student."));
        }
    }

    @PostMapping("/updateAvailability")
    public RedirectView updateAvailability(@RequestParam Map<String, String> params, HttpSession session) {
        long studentId = (long) session.getAttribute("userSpecialId");
        Student student = studentService.findById(studentId).orElseThrow();
        Map<String, String> availability = new HashMap<>();

        for (String day : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}) {
            String start = params.get(day + "Start");
            String end = params.get(day + "End");

            if (start != null && end != null) {
                availability.put(day, start + " - " + end);
            }
        }

        student.setAvailability(availability);
        studentService.save(student);

        return new RedirectView("/studentprofile");
    }

    @GetMapping("/getAllStudents")
    public Iterable<Student> listAllTopics(){
        return studentService.findAll();
    }
}
