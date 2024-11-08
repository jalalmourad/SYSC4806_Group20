package org.sysc4806.sysc4806_group20;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProfModel {

    @Id
    @GeneratedValue
    Long id;
    String name;

    @OneToMany
    List<ProjectModel> projects;

    public ProfModel() {
    }
    public ProfModel(String name) {
        this.name = name;
        projects = new ArrayList<ProjectModel>();
    }

    public List<ProjectModel> getProjects(){
        return projects;
    }

    public void addProject(ProjectModel project) {
        projects.add(project);
    }

    public void removeProject(ProjectModel project) {
        projects.remove(project);
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
