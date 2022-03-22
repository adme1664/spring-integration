package com.adme.learning.cloudstream.services;

import com.adme.learning.cloudstream.models.Person;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ProducerPersonService {
    public Message<Person> sendPerson(Person person) {
        Person person1=new Person();
        person1.setFirstname(person.getFirstname());
        person1.setLastname(person.getLastname());
        person1.setSex(person.getSex());
        return MessageBuilder.withPayload(person1).build();
    }
}
