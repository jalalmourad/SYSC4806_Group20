package org.sysc4806.sysc4806_group20;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.sysc4806.sysc4806_group20.Model.ProgramRestrictions;
import org.sysc4806.sysc4806_group20.Model.Status;
import org.sysc4806.sysc4806_group20.Model.Topic;
import org.sysc4806.sysc4806_group20.Repository.TopicRepository;
import org.sysc4806.sysc4806_group20.Service.TopicService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Sysc4806Group20ApplicationTests {

    @Autowired
    private TopicRepository repository;

    @Test
    @Transactional
    public void topicPersistenceTest(){
            List<ProgramRestrictions> restrictionsList = new ArrayList();
            restrictionsList.add(ProgramRestrictions.AEROSPACE);
            Topic topic = new Topic("Object Detect", "Cool Object Detect", restrictionsList, 2, Status.DRAFT);
            topic = repository.save(topic);
            Topic topicRetrived = repository.findById(topic.getId()).orElseThrow(() -> new ResourceNotFoundException("No Topic Found With This ID"));
            assertEquals(topicRetrived.getId(), topic.getId());
            repository.deleteById(topic.getId());
    }

}
