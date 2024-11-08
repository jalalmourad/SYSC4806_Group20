package org.sysc4806.sysc4806_group20;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Sysc4806Group20Application {

    public static void main(String[] args) {
        SpringApplication.run(Sysc4806Group20Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentModelRepository repository) {
        return (args) -> {

            repository.save(new StudentModel("Jalal", "Test", 111L));
            repository.save(new StudentModel("Chloe", "Test", 222L));

        };

    }
    @Bean
    public CommandLineRunner demo2(ProfModelRepository profModelRepository) {
        return (args) -> {

            profModelRepository.save(new ProfModel("Babak"));
            profModelRepository.save(new ProfModel("Safaa"));

        };

    }



}

