package com.adme.learning.cloudstream.services;

import com.adme.learning.cloudstream.entities.EventEntity;
import com.adme.learning.cloudstream.mappers.EventMapper;
import com.adme.learning.cloudstream.mappers.PersonMapper;
import com.adme.learning.cloudstream.models.Event;
import com.adme.learning.cloudstream.models.Person;
import com.adme.learning.cloudstream.repository.EventRepository;
import com.adme.learning.cloudstream.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

@Service
@Slf4j
public class ConsumerPersonService {
    private final ObjectMapper objectMapper;
    private final Queue<Event> queueEvents;

    private final PersonRepository personRepository;
    private final EventRepository eventRepository;


    public ConsumerPersonService(ObjectMapper objectMapper,
                                 PersonRepository personRepository,
                                 EventRepository eventRepository) {
        this.objectMapper = objectMapper;
        this.queueEvents = new LinkedBlockingDeque<>();
        this.personRepository = personRepository;
        this.eventRepository = eventRepository;

    }

    public void processEvent(Event event) {
        if (event.getEventName().equals("person")) {
            Person person = this.objectMapper.convertValue(event.getEventObject(), Person.class);
            personRepository.save(PersonMapper.INSTANCE.modelToEntity(person));
            EventEntity eventEntity = EventMapper.INSTANCE.modelToEntity(event);
            eventEntity.setObjectString(event.getEventObject().toString());
            this.eventRepository.save(eventEntity);
            this.send(event);
        }
    }

    public void send(Event event) {
        this.queueEvents.add(event);
    }

    public Event processEventAndSend() {
        return Optional.ofNullable(this.queueEvents.poll())
                .map(event -> {
                    if (event.getEventName().equals("person")) {
                        log.info("Process event {}", event.getEventObject().toString());
                        return Event.builder()
                                .eventName(event.getEventName())
                                .eventObject(event.getEventObject())
                                .build();
                    } else {
                        return null;
                    }
                }).orElse(null);
    }
}
