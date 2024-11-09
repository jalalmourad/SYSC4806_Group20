package org.sysc4806.sysc4806_group20;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.sysc4806.sysc4806_group20.Model.ProgramRestrictions;
import org.sysc4806.sysc4806_group20.Model.Status;
import org.sysc4806.sysc4806_group20.Model.Topic;
import org.sysc4806.sysc4806_group20.Repository.TopicRepository;

import java.util.ArrayList;

@SpringBootTest
class Sysc4806Group20ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    @Bean
    @Transactional
    public CommandLineRunner demo(TopicRepository repository){
        return (args) -> {

            Topic topic = new Topic("SLIME", "TEST", new ArrayList<ProgramRestrictions>(), 0, Status.DRAFT);
            repository.save(topic);
            repository.findAll().forEach(topicial -> {
                System.out.println(topicial.getId());
            });

        };
    }

}
