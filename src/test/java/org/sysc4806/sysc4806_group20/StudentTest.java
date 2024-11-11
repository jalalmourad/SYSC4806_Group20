package org.sysc4806.sysc4806_group20;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sysc4806.sysc4806_group20.Model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    private Student student;

    @BeforeEach
    public void setup(){
        student = new Student();
    }

    @Test
    public void checkStudentSettersAndGetters(){
        String firstName = "JOHN";
        String lastName = "Doe";
        Long studentID = 1L;

        student.setStudentID(studentID);
        student.setFirstName(firstName);
        student.setLastName(lastName);

        assertEquals(studentID, student.getStudentID());
        assertEquals(firstName, student.getFirstName());
        assertEquals(lastName, student.getLastName());
    }

}
