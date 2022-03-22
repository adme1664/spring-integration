package com.adme.learning.cloudstream.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
