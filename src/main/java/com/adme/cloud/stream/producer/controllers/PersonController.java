package com.adme.cloud.stream.producer.controllers;

import com.adme.cloud.stream.producer.models.Event;
import com.adme.cloud.stream.producer.models.Person;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final StreamBridge streamBridge;

    public PersonController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/")
    public boolean sendPerson(@RequestBody Person person) {
        Person p = Person.builder()
                .firstname(person.getFirstname())
                .lastname(person.getLastname())
                .sex(person.getSex())
                .id(person.getId())
                .build();
        Map<String,Object> personMap=Map.of("person",true);
        Event event= Event.builder()
                .eventName("person")
                .eventObject(p)
                .build();
        return streamBridge.send("admeEvent",
                MessageBuilder.createMessage(event, new MessageHeaders(personMap)));
    }
}
