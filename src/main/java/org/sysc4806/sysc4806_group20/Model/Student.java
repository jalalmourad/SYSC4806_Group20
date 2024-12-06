package org.sysc4806.sysc4806_group20.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private Long studentID;
    private boolean hasTopic;
    private String fileName;

    private boolean submitted;

    @OneToOne
    private Topic joinedTopic;

    @ElementCollection
    private Map<String, String> availability;

    @ElementCollection
    private List<String> uploads;

    public Student() {

    }

    public Student(String firstName, String lastName, Long studentID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.hasTopic = false;
        this.joinedTopic = null;
        this.submitted = false;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + ", ID: " + studentID;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Long getStudentID() {
        return studentID;
    }
    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }
    public void setHasTopic(boolean hasTopic){
        this.hasTopic=hasTopic;
    }
    public boolean getHasTopic(){
        return hasTopic;
    }
    public boolean isSubmitted(){
        return submitted;
    }
    public void setSubmitted(boolean submitted){
        this.submitted=submitted;
    }
    public String getFileName(){
        return fileName;
    }
    public void setFileName(String fileName){
        this.fileName=fileName;
    }


    public Map<String, String> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, String> availability) {
        this.availability = availability;
    }

    public List<String> getUploads() {
        return uploads;
    }

    public void setUploads(List<String> uploads) {
        this.uploads = uploads;
    }

    public void addUpload(String upload){
        this.uploads.add(upload);
    }

    public Topic getJoinedTopic() {
        return joinedTopic;
    }
    public void setJoinedTopic(Topic joinedTopic) {
        this.hasTopic = true;
        this.joinedTopic = joinedTopic;
    }
}
