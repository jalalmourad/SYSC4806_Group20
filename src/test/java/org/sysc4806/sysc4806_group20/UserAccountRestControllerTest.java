package org.sysc4806.sysc4806_group20;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAccountRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCheckUser_UsernameAvailablePasswordValid() {
        Map<String, String> request = new HashMap<>();
        request.put("username", "newuser");
        request.put("password", "securePassword");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/userAccount/validate",
                new HttpEntity<>(request),
                Map.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.get("success")).isEqualTo(true);
        assertThat(responseBody.get("message")).isEqualTo("Correct");
    }

    @Test
    void testCheckUser_UsernameTaken() {
        // Step 1: Create a new professor
        Map<String, String> professorRequest = new HashMap<>();
        professorRequest.put("firstName", "John");
        professorRequest.put("lastName", "Doe");
        professorRequest.put("coordinator", "false");
        professorRequest.put("username", "existinguser");
        professorRequest.put("password", "password"); // Use a raw password, it will be hashed in the controller

        // Create the professor
        ResponseEntity<Map> professorResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/professors/newProfessor?firstName={firstName}&lastName={lastName}&coordinator={coordinator}&username={username}&password={password}",
                null,
                Map.class,
                professorRequest
        );

        // Verify the professor creation was successful
        assertThat(professorResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> professorResponseBody = professorResponse.getBody();
        assertThat(professorResponseBody).isNotNull();
        assertThat(professorResponseBody.get("success")).isEqualTo(true);
        assertThat(professorResponseBody.get("message")).isEqualTo("Professor created successfully.");

        // Step 2: Check if the username already exists
        Map<String, String> request = new HashMap<>();
        request.put("username", "existinguser");
        request.put("password", "securePassword");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/userAccount/validate",
                new HttpEntity<>(request),
                Map.class
        );

        // Assert that the username is now taken
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.get("success")).isEqualTo(false);
        assertThat(responseBody.get("message")).isEqualTo("Username already Taken.");
    }


    @Test
    void testLogin_InvalidCredentials() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("username", "testuser");
        requestParams.put("password", "wrongPassword");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/userAccount/login?username={username}&password={password}",
                null,
                Map.class,
                requestParams
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.get("success")).isEqualTo(false);
        assertThat(responseBody.get("message")).isEqualTo("Invalid credentials!");
    }
}
