package org.sysc4806.sysc4806_group20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/prof")
public class ProfRestController {

    @Autowired
    ProfModelRepository profModelRepository;

    @GetMapping("/{name}")
    public Optional<ProfModel> getProf(@PathVariable String name){
        return Optional.ofNullable(profModelRepository.findByName(name));

    }

    @PostMapping("/addProf")
    public ProfModel addProf(@RequestBody ProfModel profModel){
       return profModelRepository.save(profModel);
    }

}
