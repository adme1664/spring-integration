package com.example.person.file.gateway.models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Builder
@AllArgsConstructor
public class Person {
    @CsvBindByName
    String firstname;
    @CsvBindByName
    String lastname;
    @CsvBindByName
    String sex;
    @CsvBindByName
    Timestamp dob;
    @CsvBindByName
    String address;
    @CsvBindByName
    String phoneNumber;

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", sex='" + sex + '\'' +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
