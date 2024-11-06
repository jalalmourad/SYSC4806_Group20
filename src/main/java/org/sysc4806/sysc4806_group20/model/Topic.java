package org.sysc4806.sysc4806_group20.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private List<ProgramRestictions> programRestrictions;

    private int numberofStudents;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private List<Student> students;

    @OneToMany
    private Prof prof;

    public Topic() {}

    public Topic(String title, String description, List<ProgramRestictions> programRestrictions, int numberofStudents, Status status)
    {
        this.title = title;
        this.description = description;
        this.programRestrictions = programRestrictions;
        this.numberofStudents = numberofStudents;
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
    public List<ProgramRestictions> getProgramRestrictions()
    {
        return programRestrictions;
    }
    public int getNumberofStudents()
    {
        return numberofStudents;
    }
    public Status getStatus()
    {
        return status;
    }
    public List<Student> getStudents() {
        return students;
    }
    public Prof getProf() {
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
    public void setProgramRestrictions(List<ProgramRestictions> programRestrictions)
    {
        this.programRestrictions = programRestrictions;
    }
    public void setNumberofStudents(int numberofStudents)
    {
        this.numberofStudents = numberofStudents;
    }
    public void setStatus(Status status)
    {
        this.status = status;
    }
}
