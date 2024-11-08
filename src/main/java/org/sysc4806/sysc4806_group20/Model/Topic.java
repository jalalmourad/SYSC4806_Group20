package org.sysc4806.sysc4806_group20.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;

    @ElementCollection(targetClass = ProgramRestrictions.class)
    @Enumerated(EnumType.STRING)
    private List<ProgramRestrictions> programRestrictions;

    private int numberOfStudents;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    private List<Student> students;

    @ManyToOne
    private Professor prof;

    public Topic() {}

    public Topic(String title, String description, List<ProgramRestrictions> programRestrictions, int numberofStudents, Status status)
    {
        this.title = title;
        this.description = description;
        this.programRestrictions = programRestrictions;
        this.numberOfStudents = numberofStudents;
        this.status = status;
    }

    public long getId()
    {
        return id;
    }
    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public List<ProgramRestrictions> getProgramRestrictions()
    {
        return programRestrictions;
    }
    public int getNumberOfStudents()
    {
        return numberOfStudents;
    }
    public Status getStatus()
    {
        return status;
    }
    public List<Student> getStudents() {
        return students;
    }
    public Professor getProf() {
        return prof;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setProgramRestrictions(List<ProgramRestrictions> programRestrictions)
    {
        this.programRestrictions = programRestrictions;
    }
    public void setNumberOfStudents(int numberOfStudents)
    {
        this.numberOfStudents = numberOfStudents;
    }
    public void setStatus(Status status)
    {
        this.status = status;
    }

    public void setProf(Professor prof) {
        this.prof = prof;
    }


}