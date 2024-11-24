package org.sysc4806.sysc4806_group20.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Model.Student;
import org.sysc4806.sysc4806_group20.Model.Topic;
import org.sysc4806.sysc4806_group20.Service.ProfessorService;
import org.sysc4806.sysc4806_group20.Service.StudentService;
import org.sysc4806.sysc4806_group20.Service.TopicService;

@RestController
@RequestMapping("/api/topics")
public class TopicRestController {

    private TopicService topicService;
    private ProfessorService professorService;
    private StudentService studentService;

    TopicRestController(TopicService topicService, ProfessorService professorService, StudentService studentService){
        this.studentService = studentService;
        this.topicService = topicService;
        this.professorService = professorService;
    }

    @PostMapping("/newTopic")
    public Topic newTopic(@RequestBody Topic topicRequest, HttpSession session){
        long id = (long) session.getAttribute("userSpecialId");
        System.out.println("Session Special ID: " + id);
        Professor profreturn = professorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found with id " + id));
        System.out.println(topicRequest.getProgramRestrictions());
        profreturn.addTopic(topicRequest);
        topicRequest.setProf(profreturn);
        System.out.println("prof id "+profreturn.getId());
        Topic topicAdded = topicService.save(topicRequest);
        professorService.save(profreturn);
        System.out.println("New Topic Created");
        return topicAdded;
    }

    //For Testing Only
    @PostMapping("/newTopic/{id}")
    public Topic newTopic(@RequestBody Topic topicRequest, @PathVariable Long id){
        Professor profreturn = professorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found with id " + id));
        System.out.println(topicRequest.getProgramRestrictions());
        profreturn.addTopic(topicRequest);
        topicRequest.setProf(profreturn);
        System.out.println("prof id "+profreturn.getId());
        Topic topicAdded = topicService.save(topicRequest);
        professorService.save(profreturn);
        System.out.println("New Topic Created");
        return topicAdded;
    }

    @GetMapping("/getAllTopics")
    public Iterable<Topic> listAllTopics(){
        return topicService.findAll();
    }


    @PostMapping("/addStudentToTopic")
    public RedirectView addStudentToTopic(@RequestParam Long topic, HttpSession session){
        long studentNum = (long) session.getAttribute("userSpecialId");
        System.out.println(topic+ " " + studentNum);
        Student student = studentService.findById(studentNum).orElseThrow(() -> new ResourceNotFoundException
                ("No Student found with id" + studentNum));
        Topic topicToAddStudent = topicService.findById(topic).orElseThrow(() -> new ResourceNotFoundException("No Topic Found"));
        topicToAddStudent.addStudent(student);
        student.setJoinedTopic(topicToAddStudent);
        topicService.save(topicToAddStudent);
        studentService.save(student);
        return new RedirectView("/" + studentNum + "/listTopics");
    }

    @DeleteMapping("/deleteTopic/{id}")
    public Long deleteTopic(@PathVariable Long id){
        topicService.deleteById(id);
        return id;
    }

}
