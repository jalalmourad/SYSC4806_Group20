package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
}
