package com.adme.learning.cloudstream.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column
    String firstname;
    @Column
    String lastname;
    @Column
    String sex;
    @Column
    String address;
    @Column
    String phoneNumber;
    @Column
    Timestamp dob;
}
