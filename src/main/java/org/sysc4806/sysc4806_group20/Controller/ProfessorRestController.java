package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Service.ProfessorService;

@RestController
@RequestMapping("/api/professors")
public class ProfessorRestController {
    @Autowired
    private ProfessorService professorService;

    @PostMapping("/newProfessor")
    public Professor newProfessor(@RequestParam(value = "firstName") String firstName,
                                  @RequestParam(value = "lastName") String lastName){
        Professor newProf = new Professor(firstName, lastName);
        professorService.save(newProf);
        return newProf;
    }

    @DeleteMapping("/deleteProfessor")
    public Long deleteProfessor(@RequestParam(value = "id") Long id){
        professorService.deleteById(id);
        return id;
    }
}
