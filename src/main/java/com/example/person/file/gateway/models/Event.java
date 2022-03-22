package com.example.person.file.gateway.models;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class Event {
    String eventName;
    Object eventObject;
    String systemSource;

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", eventObject=" + eventObject +
                ", systemSource='" + systemSource + '\'' +
                '}';
    }
}
