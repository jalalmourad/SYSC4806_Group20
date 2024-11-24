package org.sysc4806.sysc4806_group20.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
import org.sysc4806.sysc4806_group20.Model.UserRole;
import org.sysc4806.sysc4806_group20.Service.PasswordService;
import org.sysc4806.sysc4806_group20.Service.UserAccountService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/userAccount")
public class UserAccountRestController {
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> checkUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String hashedPassword = request.get("password");

        Map<String, Object> response = new HashMap<>();

        if (userAccountService.findByUsername(username) == null) {
            if (passwordService.checkPassword(hashedPassword)) {
                response.put("success", true);
                response.put("message", "Correct");
            } else {
                response.put("success", false);
                response.put("message", "Username OK, but password too simple.");
            }
        } else {
            response.put("success", false);
            response.put("message", "Username already Taken.");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam(value = "username") String username,
                                                     @RequestParam(value = "password") String password,
                                                     HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        // Validate user credentials
        UserAccount user = userAccountService.findByUsername(username);

        if (user != null && passwordService.verifyPassword(password, user.getPassword())) {
            // Set session attributes
            long id;
            if(user.getUserRole() == UserRole.PROFESSOR)
            {
                id = user.getProfessor().getId();
            }
            else if(user.getUserRole() == UserRole.STUDENT)
            {
                id = user.getStudent().getId();
            }
            else
            {
                id = 0;
            }
            session.setAttribute("userId", user.getId());
            session.setAttribute("userSpecialId", id);
            session.setAttribute("userRole", "ROLE_" + user.getUserRole());


            response.put("success", true);
            response.put("message", "Login successful!");
            response.put("userId", user.getId());
            response.put("userRole", user.getUserRole().name());
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials!");
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "Logged out successfully!";
    }
}
