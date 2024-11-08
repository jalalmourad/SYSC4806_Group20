package org.sysc4806.sysc4806_group20;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProjectModel {

    @Id
    @GeneratedValue
    Long id;
    String Name;
    String Description;

    @OneToMany
    List<StudentModel> students;

    @ManyToOne
    ProfModel prof;

    public ProjectModel() {

    }
    public ProjectModel(Long id, String Name) {
        this.id = id;
        this.Name = Name;
        students = new ArrayList<StudentModel>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setProf(ProfModel prof) {
        this.prof = prof;
    }

    public void addStudent(StudentModel student) {
        students.add(student);
    }
    public void removeStudent(StudentModel student) {
        students.remove(student);
    }

    public List<StudentModel> getStudents() {
        return students;
    }

    public ProfModel getProf() {
        return prof;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }

}
