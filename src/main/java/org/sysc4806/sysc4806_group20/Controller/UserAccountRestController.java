package org.sysc4806.sysc4806_group20.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
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
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password,
                        HttpSession session) {
        // Validate user credentials
        UserAccount user = userAccountService.validateCredentials(username, password);

        if (user != null) {
            // Set session attributes
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getUserRole());
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "Logged out successfully!";
    }
}
