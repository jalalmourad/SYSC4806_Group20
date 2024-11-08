package org.sysc4806.sysc4806_group20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/{id}")
    public Optional<ProjectModel> getProject(@PathVariable Long id){
        return projectRepository.findById(id);
    }

    @PostMapping("/createProject")
    public ProjectModel createProject(@RequestBody ProjectModel project){
        return projectRepository.save(project);
    }


}
