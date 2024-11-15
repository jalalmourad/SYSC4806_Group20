package org.sysc4806.sysc4806_group20.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Topic> topics;
    private String firstName;
    private String lastName;

    public Professor() {
    }

    public Professor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.topics = new ArrayList<>();
    }



    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String toString(){
        return firstName + " " + lastName;
    }

    public void addTopic(Topic newTopic){
        topics.add(newTopic);
    }

    public void removeTopic(Topic removeTopic){
        topics.remove(removeTopic);
    }
    public List<Topic> getTopics(){
        return topics;
    }
}
