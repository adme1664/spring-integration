package com.example.person.file.gateway.services;

import com.example.person.file.gateway.models.Event;
import com.example.person.file.gateway.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

@Service
@Slf4j
public class PersonService {

    private static final String PERSON = "person";
    private final Queue<Event> queueEvents;
    private final StreamBridge streamBridge;

    public PersonService(StreamBridge streamBridge) {
        this.queueEvents = new LinkedBlockingDeque<>();
        this.streamBridge = streamBridge;
        log.info("Create object");
    }

    public void transformPersonToEvent(@NotNull Person person) {
        try {
            var event = Event.builder()
                    .eventName(PERSON)
                    .eventObject(person).build();
            this.sendEvent(event);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex.getCause());
        }
    }

    private void sendEvent(Event event) {
        this.queueEvents.add(event);
    }

    public Event processEventPerson() {
        Map<String, Object> personMap = Map.of(PERSON, true);
        return Optional.ofNullable(this.queueEvents.poll())
                .map(event -> {
                    if (event.getEventName().equals(PERSON)) {
                        streamBridge.send("admeEvent", MessageBuilder.createMessage(event, new MessageHeaders(personMap)));
                        log.info("Event Type sent {}, event information {}",event.getEventName(), event.getEventObject());
                        return event;
                    } else {
                        return null;
                    }
                }).orElse(null);
    }
}
