package org.sysc4806.sysc4806_group20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentRestController
{
    @Autowired
    StudentModelRepository studentModelRepository;

    @GetMapping("/{id}")
    public StudentModel getStudent(@PathVariable Long id){
        Optional<StudentModel> student = studentModelRepository.findById(id);
        return student.orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    @PostMapping("/createStudent")
    public StudentModel createStudent(@RequestBody StudentModel studentModel){
        return studentModelRepository.save(studentModel);
    }


}
