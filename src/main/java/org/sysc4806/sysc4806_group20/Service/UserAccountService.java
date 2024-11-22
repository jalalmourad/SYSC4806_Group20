package org.sysc4806.sysc4806_group20.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
import org.sysc4806.sysc4806_group20.Repository.UserAccountRepository;

import java.util.Optional;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PasswordService passwordService;

    public Iterable<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    public Optional<UserAccount> findById(Long id) {
        return userAccountRepository.findById(id);
    }

    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    public UserAccount findByUsername(String username) { return userAccountRepository.findByUsername(username); }

    public void deleteById(Long id) {
        userAccountRepository.deleteById(id);
    }

    public UserAccount validateCredentials(String username, String password) {
        UserAccount userAccount = findByUsername(username);
        if(userAccount == null) {
            return null;
        }
        else if(passwordService.verifyPassword(password, userAccount.getPassword())) {
            return userAccount;
        }
        return null;
    }
}
