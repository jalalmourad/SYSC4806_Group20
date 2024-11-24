package org.sysc4806.sysc4806_group20;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.sysc4806.sysc4806_group20.Model.UserAccount;
import org.sysc4806.sysc4806_group20.Repository.UserAccountRepository;
import org.sysc4806.sysc4806_group20.Service.PasswordService;
import org.sysc4806.sysc4806_group20.Service.UserAccountService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    @MockBean
    private UserAccountRepository userAccountRepository;

    @MockBean
    private PasswordService passwordService;

    private UserAccount testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserAccount();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("hashedPassword123");

        when(userAccountRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userAccountRepository.findByUsername("testuser")).thenReturn(testUser);
        when(passwordService.verifyPassword("rawPassword", "hashedPassword123")).thenReturn(true);
    }

    @Test
    void testFindAll() {
        Iterable<UserAccount> users = List.of(testUser);
        when(userAccountRepository.findAll()).thenReturn(users);

        Iterable<UserAccount> result = userAccountService.findAll();
        assertNotNull(result);
        assertEquals(users, result);
        verify(userAccountRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Optional<UserAccount> result = userAccountService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userAccountRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(userAccountRepository.save(testUser)).thenReturn(testUser);

        UserAccount result = userAccountService.save(testUser);
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(userAccountRepository, times(1)).save(testUser);
    }

    @Test
    void testFindByUsername() {
        UserAccount result = userAccountService.findByUsername("testuser");
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(userAccountRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testDeleteById() {
        doNothing().when(userAccountRepository).deleteById(1L);

        userAccountService.deleteById(1L);
        verify(userAccountRepository, times(1)).deleteById(1L);
    }

    @Test
    void testValidateCredentialsSuccess() {
        UserAccount result = userAccountService.validateCredentials("testuser", "rawPassword");
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(userAccountRepository, times(1)).findByUsername("testuser");
        verify(passwordService, times(1)).verifyPassword("rawPassword", "hashedPassword123");
    }

    @Test
    void testValidateCredentialsFailure() {
        when(passwordService.verifyPassword("wrongPassword", "hashedPassword123")).thenReturn(false);

        UserAccount result = userAccountService.validateCredentials("testuser", "wrongPassword");
        assertNull(result);
        verify(userAccountRepository, times(1)).findByUsername("testuser");
        verify(passwordService, times(1)).verifyPassword("wrongPassword", "hashedPassword123");
    }
}
