package org.sysc4806.sysc4806_group20;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sysc4806.sysc4806_group20.Model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class TopicTest {

    private Topic topic;
    private List<ProgramRestrictions> programRestrictions;

    @BeforeEach
    public void setUp() {
        programRestrictions = new ArrayList<>();
        programRestrictions.add(ProgramRestrictions.AEROSPACE);

        topic = new Topic("Object Detection", "An advanced topic on object detection", programRestrictions, 0, Status.DRAFT);
    }

    @Test
    public void testGetters() {
        assertEquals("Object Detection", topic.getTitle());
        assertEquals("An advanced topic on object detection", topic.getDescription());
        assertEquals(programRestrictions, topic.getProgramRestrictions());
        assertEquals(0, topic.getNumberOfStudents());
        assertEquals(Status.DRAFT, topic.getStatus());
    }

    @Test
    public void testSetters() {
        topic.setTitle("New Title");
        topic.setDescription("Updated description");
        List<ProgramRestrictions> newRestrictions = new ArrayList<>();
        newRestrictions.add(ProgramRestrictions.SOFTWARE);
        topic.setProgramRestrictions(newRestrictions);
        topic.setNumberOfStudents(5);
        topic.setStatus(Status.FULL);

        assertEquals("New Title", topic.getTitle());
        assertEquals("Updated description", topic.getDescription());
        assertEquals(newRestrictions, topic.getProgramRestrictions());
        assertEquals(5, topic.getNumberOfStudents());
    }

    @Test
    public void testAddStudent() {
        Student mockStudent = mock(Student.class);
        topic.addStudent(mockStudent);

        assertEquals(1, topic.getNumberOfStudents());
        assertTrue(topic.getStudents().contains(mockStudent));
    }

    @Test
    public void testSetProfessor() {
        Professor mockProfessor = mock(Professor.class);
        topic.setProf(mockProfessor);

        assertEquals(mockProfessor, topic.getProf());
    }

}
