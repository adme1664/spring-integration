package com.adme.learning.cloudstream.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Builder
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column
    String eventName;
    @Column
    String objectString;
    @Column
    String systemSource;
    @CreatedDate
    LocalDate dateCreation;
    @LastModifiedBy
    LocalDate dateUpdate;

}
