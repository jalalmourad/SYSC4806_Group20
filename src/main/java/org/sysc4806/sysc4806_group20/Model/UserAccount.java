package org.sysc4806.sysc4806_group20.Model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Professor prof;

    public UserAccount() {}

    public UserAccount(String username, String password, UserRole userRole)
    {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
    public long getId()
    {
        return id;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    public Professor getProfessor() {
        return prof;
    }
    public Student getStudent() {
        return student;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    public void setProfessor(Professor prof) {
        this.prof = prof;
        this.student = null;
    }
    public void setStudent(Student student) {
        this.student = student;
        this.prof = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return roles as authorities
        return Collections.singleton(() -> "ROLE_" + userRole.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
