package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
import org.sysc4806.sysc4806_group20.Service.PasswordService;
import org.sysc4806.sysc4806_group20.Service.ProfessorService;
import org.sysc4806.sysc4806_group20.Service.UserAccountService;

import java.util.Map;

import static org.sysc4806.sysc4806_group20.Model.UserRole.PROFESSOR;
import static org.sysc4806.sysc4806_group20.Model.UserRole.STUDENT;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> newProfessor(@RequestParam(value = "firstName") String firstName,
                                          @RequestParam(value = "lastName") String lastName,
                                          @RequestParam(value = "coordinator") Boolean coordinator,
                                          @RequestParam(value = "username") String username,
                                          @RequestParam(value = "password") String hashPassword) {

        // Validate inputs
        if (firstName == null || lastName == null || username == null || hashPassword == null ||
                firstName.isBlank() || lastName.isBlank() || username.isBlank() || hashPassword.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "All fields are required."));
        }

        try {
            // Create and save Professor
            Professor newProf = new Professor(firstName, lastName, coordinator);
            Professor savedProfessor = professorService.save(newProf);

            // Hash password
            String hashedPassword = passwordService.hashToSave(hashPassword);

            // Create and save UserAccount
            UserAccount userAccount = new UserAccount(username, hashedPassword, PROFESSOR);
            userAccount.setProfessor(savedProfessor);
            userAccountService.save(userAccount);

            // Respond with success marker
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Professor created successfully.",
                    "professor", savedProfessor // Include necessary details or use a DTO
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "An error occurred while creating the professor."));
        }
    }

    @DeleteMapping("/deleteProfessor")
    public Long deleteProfessor(@RequestParam(value = "id") Long id){
        professorService.deleteById(id);
        return id;
    }

    @PostMapping("/updateAvailability")
    public RedirectView updateAvailability(@RequestParam Map<String, String> params, @RequestParam(value = "id") Long professorId) {
        Professor professor = professorService.findById(professorId).orElseThrow();
        Map<String, String> availability = new HashMap<>();

        for (String day : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}) {
            String start = params.get(day + "Start");
            String end = params.get(day + "End");

            if (start != null && end != null) {
                availability.put(day, start + " - " + end);
            }
        }

        professor.setAvailability(availability);
        professorService.save(professor);

        return new RedirectView("/" + professorId + "/profprofile");
    }
}
