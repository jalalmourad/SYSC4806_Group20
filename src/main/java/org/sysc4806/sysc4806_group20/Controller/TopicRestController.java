package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Model.Topic;
import org.sysc4806.sysc4806_group20.Service.ProfessorService;
import org.sysc4806.sysc4806_group20.Service.TopicService;

import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicRestController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private ProfessorService professorService;

    @PostMapping("/newTopic/{id}")
    public Topic newTopic(@RequestBody Topic topicRequest, @PathVariable Long id){
        Professor profreturn = professorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found with id " + id));
        profreturn.addTopic(topicRequest);
        topicRequest.setProf(profreturn);
        topicService.save(topicRequest);
        professorService.save(profreturn);
        System.out.println("New Topic Created");
        return topicRequest;
    }

}
