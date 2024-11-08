package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Model.ProgramRestrictions;
import org.sysc4806.sysc4806_group20.Model.Status;
import org.sysc4806.sysc4806_group20.Model.Student;
import org.sysc4806.sysc4806_group20.Service.ProfessorService;
import org.sysc4806.sysc4806_group20.Service.StudentService;
import org.sysc4806.sysc4806_group20.Service.TopicService;

import java.util.EnumSet;
import java.util.ArrayList;

@Controller
public class ViewController {
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    /**
     * Testing
     */
    @GetMapping("/listTopics")
    public String topicList(Model model){
        model.addAttribute("topics", topicService.findAll());
        return "topicList";
    }

    /**
     * For Testing only, maybe moved somewhere else later
     */
    @GetMapping("/{id}/newTopic")
    public String newTopic(@PathVariable Long id, Model model){
        model.addAttribute("status", new ArrayList<>(EnumSet.allOf(Status.class)));
        model.addAttribute("programs", new ArrayList<>(EnumSet.allOf(ProgramRestrictions.class)));
        model.addAttribute("profId", id);
        return "newTopic";
    }

    /**
     * For Testing
     */
    @GetMapping("/{id}/profprofile")
    public String profProfile(@PathVariable Long id, Model model){
        Professor profreturn = professorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found with id " + id));
        model.addAttribute("professor", profreturn);
        System.out.println(profreturn);
        return "ProfProfile";
    }

    /**
     * For Testing
     */
    @GetMapping("/{id}/studentprofile")
    public String studentProfile(@PathVariable Long id, Model model){
        Student studentreturn = studentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        model.addAttribute("student", studentreturn);
        System.out.println(studentreturn);
        return "StudentProfile";
    }
}
