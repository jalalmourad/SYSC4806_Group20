package org.sysc4806.sysc4806_group20.Service;

import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PasswordService {

    private String unHashWeb(String hashedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(hashedPassword);
        return new String(decodedBytes);
    }

    private String hashForStorage(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String hashToSave(String hashedPassword) {
        String password = unHashWeb(hashedPassword);
        return hashForStorage(password);
    }

    public Boolean checkPassword(String hashedPassword) {
        String password = unHashWeb(hashedPassword);
        List<String> restrictedPasswords = new ArrayList<>();
        // Read the restricted passwords from a text file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/sysc4806/sysc4806_group20/Service/restricted_passwords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                restrictedPasswords.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading the restricted passwords file: " + e.getMessage());
            return false; // Assume false if the file cannot be read
        }

        // Check if the password matches any in the list
        for (String restrictedPassword : restrictedPasswords) {
            if (password.equals(restrictedPassword)) {
                return false; // Invalid password if it matches a restricted password
            }
        }

        return true; // Password is valid
    }

    public boolean verifyPassword(String hashedPassword, String password) {
        String decodedHash = unHashWeb(hashedPassword);
        return BCrypt.checkpw(decodedHash, password);
    }

}
