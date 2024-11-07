package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sysc4806.sysc4806_group20.Service.ProfessorService;

@RestController
@RequestMapping("/api/professors")
public class ProfessorRestController {
    private ProfessorService professorService;


}
