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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicProfRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateProf() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("firstName", "John");
        requestParams.put("lastName", "Doe");
        requestParams.put("coordinator", "false");
        requestParams.put("username", "johndoe");
        requestParams.put("password", "cGFzc3dvcmQ="); // Base64 for "password"

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/professors/newProfessor?firstName={firstName}&lastName={lastName}&coordinator={coordinator}&username={username}&password={password}",
                null,
                Map.class,
                requestParams
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Extract the response body
        Map responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.get("success")).isEqualTo(true);
        assertThat(responseBody.get("message")).isEqualTo("Professor created successfully.");

        // Verify the professor data
        Map professorData = (Map) responseBody.get("professor");
        assertThat(professorData).isNotNull();
        assertThat(professorData.get("id")).isNotNull();
        assertThat(professorData.get("firstName")).isEqualTo("John");
        assertThat(professorData.get("lastName")).isEqualTo("Doe");
        System.out.println("Created Professor: " + professorData);

    }

    @Test
    public void testCreateTopic() {
        testCreateProf();
        List<ProgramRestrictions> programs = new ArrayList<>();
        programs.add(ProgramRestrictions.CIVIL);
        Topic newTopic = new Topic("Topic 1","New Topic descrription", programs, 30, Status.OPEN);
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
