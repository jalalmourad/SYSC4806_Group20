package org.sysc4806.sysc4806_group20;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.sysc4806.sysc4806_group20.Model.Topic;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicStudentRestController {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateStudent(){
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("firstName", "John");
        requestParams.put("lastName", "Doe");
        requestParams.put("studentNumber", "1122444553");
        requestParams.put("username", "johndoe");
        requestParams.put("password", "cGFzc3dvcmQ="); // Base64 for "password"

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/students/newStudent?firstName={firstName}&lastName={lastName}&studentNumber={studentNumber}&username={username}&password={password}",
                null,
                Map.class,
                requestParams
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Extract the response body
        Map responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.get("success")).isEqualTo(true);
        assertThat(responseBody.get("message")).isEqualTo("Student created successfully.");

        // Verify the professor data
        Map professorData = (Map) responseBody.get("student");
        assertThat(professorData).isNotNull();
        assertThat(professorData.get("id")).isNotNull();
        assertThat(professorData.get("firstName")).isEqualTo("John");
        assertThat(professorData.get("lastName")).isEqualTo("Doe");
        System.out.println("Created Student: " + professorData);
    }

    @Test
    public void testGetAllStudents(){
        ResponseEntity<Topic[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/students/getAllStudents", Topic[].class
        );
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }
}
