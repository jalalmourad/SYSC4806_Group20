package org.sysc4806.sysc4806_group20;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sysc4806.sysc4806_group20.Model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ProfessorTest {

    Professor professor;
    List<Topic> topicList;

    @BeforeEach
    public void setUp(){
        professor = new Professor();
        topicList = new ArrayList<>();
    }

    @Test
    public void testSettersAndGetters(){
        String firstName = "KAHN";
        String lastName = "Does";

        professor.setFirstName(firstName);
        professor.setLastName(lastName);

        assertEquals(professor.getLastName(), lastName);
        assertEquals(professor.getFirstName(), firstName);

    }

    @Test
    public void testSetTopic(){
        Topic mockTopic = mock(Topic.class);

        professor.addTopic(mockTopic);
        topicList.add(mockTopic);

        assertEquals(topicList, professor.getTopics());
    }


}
