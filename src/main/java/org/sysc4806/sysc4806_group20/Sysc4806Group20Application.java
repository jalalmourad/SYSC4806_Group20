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

    @Bean
    @Transactional
    public CommandLineRunner demo(TopicRepository repository){		//WOAH DI!
        return (args) -> {

            Topic topic = new Topic("SLIME", "The engineering team developed an innovative solution to improve the efficiency of solar panels by integrating advanced materials and cutting-edge technology, aiming to reduce energy costs and enhance sustainability.", new ArrayList<ProgramRestrictions>(), 33, Status.DRAFT);
            repository.save(topic);


            repository.findAll().forEach(topicial -> {
                System.out.println(topicial.getId());
            });

        };
    }

}
