package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sysc4806.sysc4806_group20.Model.Student;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
import org.sysc4806.sysc4806_group20.Model.UserRole;
import org.sysc4806.sysc4806_group20.Service.PasswordService;
import org.sysc4806.sysc4806_group20.Service.StudentService;
import org.sysc4806.sysc4806_group20.Service.UserAccountService;

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
    public Student newStudent(@RequestParam(value = "firstName") String firstName,
                                  @RequestParam(value = "lastName") String lastName,
                                @RequestParam(value = "studentNumber") Long studentId,
                              @RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String hashPassword){
        Student newStud = new Student(firstName, lastName, studentId);
        Student student = studentService.save(newStud);
        String password = passwordService.hashToSave(hashPassword);
        UserAccount userAccount = new UserAccount(username, password, STUDENT);
        userAccount.setStudent(student);
        userAccountService.save(userAccount);
        return newStud;
    }
}
