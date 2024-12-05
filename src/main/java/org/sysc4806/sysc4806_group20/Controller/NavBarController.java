package org.sysc4806.sysc4806_group20.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/navBar")
public class NavBarController {

    @GetMapping("/getRole")
    public String getRole(HttpSession session) {
        if (session.getAttribute("userRole") == null) {
            return "Guest";
        } else if (session.getAttribute("userRole").equals("ROLE_STUDENT")) {
            return "Student";
        } else if (session.getAttribute("userRole").equals("ROLE_PROFESSOR")) {
            return "Professor";
        } else if (session.getAttribute("userRole").equals("ROLE_ADMIN")) {
            return "Admin";
        } else {
            return "Guest";
        }
    }
}