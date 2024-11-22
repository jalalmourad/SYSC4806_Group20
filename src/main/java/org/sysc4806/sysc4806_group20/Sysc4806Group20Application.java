package org.sysc4806.sysc4806_group20;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sysc4806.sysc4806_group20.Model.*;
import org.sysc4806.sysc4806_group20.Repository.TopicRepository;
import org.sysc4806.sysc4806_group20.Repository.UserAccountRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Sysc4806Group20Application {

    public static void main(String[] args) {
        SpringApplication.run(Sysc4806Group20Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserAccountRepository userAccountRepository) {
        return (args) -> {
            // Create an admin account if one doesn't exist
            if (userAccountRepository.findByUsername("admin") == null) {
                UserAccount admin = new UserAccount("admin", "$2a$10$SI/xXe2E9CGVQiCfiecMQu0gEA9.sSDGatJU4rhC2T91l.31Wq5IC", UserRole.ADMIN);
                userAccountRepository.save(admin);
            }
        };
    }

}
