package org.sysc4806.sysc4806_group20.Service;

import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Base64;
import java.util.List;

@Service
public class PasswordService {
    private final RestrictedPasswords restrictedPasswords = new RestrictedPasswords();

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
        // Check if the password matches any in the list
        if(restrictedPasswords.restrictedPasswords.contains(password)) {
            return false; // Invalid password if it matches a restricted password
        }
        return true; // Password is valid
    }

    public boolean verifyPassword(String hashedPassword, String password) {
        String decodedHash = unHashWeb(hashedPassword);
        return BCrypt.checkpw(decodedHash, password);
    }
}
