package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.sysc4806.sysc4806_group20.Model.ProgramRestrictions;
import org.sysc4806.sysc4806_group20.Model.Status;

import java.util.EnumSet;
import java.util.ArrayList;

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
        model.addAttribute("status", new ArrayList<>(EnumSet.allOf(Status.class)));
        model.addAttribute("programs", new ArrayList<>(EnumSet.allOf(ProgramRestrictions.class)));
        return "newTopic";
    }
}
