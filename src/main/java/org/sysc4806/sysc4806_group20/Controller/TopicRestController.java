package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/newTopic/{id}")
    public Topic newTopic(@RequestBody Topic topicRequest, @PathVariable Long id){
        Professor profreturn = professorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found with id " + id));
        System.out.println(topicRequest.getProgramRestrictions());
        profreturn.addTopic(topicRequest);
        topicRequest.setProf(profreturn);
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
    public Topic addStudentToTopic(@RequestParam Long topic, @RequestParam Long studentNum){
        System.out.println(topic+ " " + studentNum);
        Student student = studentService.findById(studentNum).orElseThrow(() -> new ResourceNotFoundException
                ("No Student found with id" + studentNum));
        Topic topicToAddStudent = topicService.findById(topic).orElseThrow(() -> new ResourceNotFoundException("No Topic Found"));
        topicToAddStudent.addStudent(student);
        topicService.save(topicToAddStudent);
        return topicToAddStudent;
    }

    @DeleteMapping("/deleteTopic/{id}")
    public Long deleteTopic(@PathVariable Long id){
        topicService.deleteById(id);
        return id;
    }

}
