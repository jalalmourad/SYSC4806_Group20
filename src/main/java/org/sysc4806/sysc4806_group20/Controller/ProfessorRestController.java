package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Service.ProfessorService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/professors")
public class ProfessorRestController {
    @Autowired
    private ProfessorService professorService;

    @PostMapping("/newProfessor")
    public Professor newProfessor(@RequestParam(value = "firstName") String firstName,
                                  @RequestParam(value = "lastName") String lastName,
                                  @RequestParam(value = "coordinator") Boolean coordinator){
        Professor newProf = new Professor(firstName, lastName,coordinator);
        professorService.save(newProf);
        return newProf;
    }

    @DeleteMapping("/deleteProfessor")
    public Long deleteProfessor(@RequestParam(value = "id") Long id){
        professorService.deleteById(id);
        return id;
    }

    @PostMapping("/updateAvailability")
    public RedirectView updateAvailability(@RequestParam Map<String, String> params, @RequestParam(value = "id") Long professorId) {
        Professor professor = professorService.findById(professorId).orElseThrow();
        Map<String, String> availability = new HashMap<>();

        for (String day : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}) {
            String start = params.get(day + "Start");
            String end = params.get(day + "End");

            if (start != null && end != null) {
                availability.put(day, start + " - " + end);
            }
        }

        professor.setAvailability(availability);
        professorService.save(professor);

        return new RedirectView("/" + professorId + "/profprofile");
    }
}
