package org.sysc4806.sysc4806_group20;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Model.ProgramRestrictions;
import org.sysc4806.sysc4806_group20.Model.Status;
import org.sysc4806.sysc4806_group20.Model.Topic;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicProfRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateProf(){
        Professor newProf = new Professor("John", "Doe");

        ResponseEntity<Professor> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/professors/newProfessor?firstName=John&lastName=Doe", null, Professor.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        System.out.println(response);
        System.out.println("Created Professor: " + response.getBody());
    }

    @Test
    public void testCreateTopic() {
        testCreateProf();
        List<ProgramRestrictions> programs = new ArrayList<>();
        programs.add(ProgramRestrictions.CIVIL);
        Topic newTopic = new Topic("Topic 1","New Topic descrription", programs, 30, Status.DRAFT);
        //addressBook.setId(1L);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/topics/newTopic/1", newTopic, String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        //assertThat(response.getBody().getId()).isNotNull();
        System.out.println(response);
        System.out.println("Created Topic: " + response.getBody());
    }

    @Test
    public void testGetAllTopics() {
        ResponseEntity<Topic[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/topics/getAllTopics", Topic[].class
        );
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();

    }
}
