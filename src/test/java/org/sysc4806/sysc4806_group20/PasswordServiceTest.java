package org.sysc4806.sysc4806_group20;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.sysc4806.sysc4806_group20.Service.PasswordService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordServiceTest {

    @Autowired
    private PasswordService passwordService;

    // Helper method to encode passwords for testing
    private String encodeForWeb(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    @Test
    void testHashToSave() {
        String rawPassword = "testPassword";
        String encodedPassword = encodeForWeb(rawPassword);

        String hashedPassword = passwordService.hashToSave(encodedPassword);

        assertNotNull(hashedPassword);
        assertTrue(BCrypt.checkpw(rawPassword, hashedPassword));
    }

    @Test
    void testCheckPasswordValid() {
        String validPassword = encodeForWeb("securePassword123");
        assertTrue(passwordService.checkPassword(validPassword));
    }

    @Test
    void testCheckPasswordInvalid() {
        String restrictedPassword = encodeForWeb("Password1!");
        assertFalse(passwordService.checkPassword(restrictedPassword));
        restrictedPassword = encodeForWeb("Adventure2024!");
        assertFalse(passwordService.checkPassword(restrictedPassword));
        restrictedPassword = encodeForWeb("Electric123#");
        assertFalse(passwordService.checkPassword(restrictedPassword));
    }

    @Test
    void testVerifyPasswordSuccess() {
        String rawPassword = "mySecurePassword";
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        String encodedPassword = encodeForWeb(rawPassword);

        assertTrue(passwordService.verifyPassword(encodedPassword, hashedPassword));
    }

    @Test
    void testVerifyPasswordFailure() {
        String rawPassword = "mySecurePassword";
        String wrongPassword = "wrongPassword";
        String wrongHashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        String encodedPassword = encodeForWeb(wrongPassword);

        assertFalse(passwordService.verifyPassword(encodedPassword, wrongHashedPassword));
    }
}
