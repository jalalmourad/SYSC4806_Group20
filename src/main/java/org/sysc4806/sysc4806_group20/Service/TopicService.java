package org.sysc4806.sysc4806_group20.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysc4806.sysc4806_group20.Model.Topic;
import org.sysc4806.sysc4806_group20.Repository.TopicRepository;

import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Iterable<Topic> findAll() {
        return topicRepository.findAll();
    }

    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    public void deleteById(Long id) {
        topicRepository.deleteById(id);
    }
}
