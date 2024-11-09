package org.sysc4806.sysc4806_group20;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sysc4806.sysc4806_group20.Model.ProgramRestrictions;
import org.sysc4806.sysc4806_group20.Model.Status;
import org.sysc4806.sysc4806_group20.Model.Topic;
import org.sysc4806.sysc4806_group20.Repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Sysc4806Group20Application {

    public static void main(String[] args) {
        SpringApplication.run(Sysc4806Group20Application.class, args);
    }

}
