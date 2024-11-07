package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /**
     * For Testing only, maybe moved somewhere else later
     */
    @GetMapping("/newTopic")
    public String newTopic(Model model){
        return
    }
}
