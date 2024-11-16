package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
import org.sysc4806.sysc4806_group20.Service.PasswordService;
import org.sysc4806.sysc4806_group20.Service.ProfessorService;
import org.sysc4806.sysc4806_group20.Service.UserAccountService;

import static org.sysc4806.sysc4806_group20.Model.UserRole.PROFESSOR;
import static org.sysc4806.sysc4806_group20.Model.UserRole.STUDENT;

@RestController
@RequestMapping("/api/professors")
public class ProfessorRestController {
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/newProfessor")
    public Professor newProfessor(@RequestParam(value = "firstName") String firstName,
                                  @RequestParam(value = "lastName") String lastName,
                                  @RequestParam(value = "username") String username,
                                  @RequestParam(value = "password") String hashPassword){
        Professor newProf = new Professor(firstName, lastName);
        Professor professor = professorService.save(newProf);
        String password = passwordService.hashToSave(hashPassword);
        UserAccount userAccount = new UserAccount(username, password, PROFESSOR);
        userAccount.setProfessor(professor);
        userAccountService.save(userAccount);
        return newProf;
    }

    @DeleteMapping("/deleteProfessor")
    public Long deleteProfessor(@RequestParam(value = "id") Long id){
        professorService.deleteById(id);
        return id;
    }
}
