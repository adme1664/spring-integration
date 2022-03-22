package com.example.person.file.gateway.utils;

import com.example.person.file.gateway.models.Person;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class PersonFileReader {

    public List<Person> fileCsvParsePerson(@NotNull String csvPathFile) throws FileNotFoundException {
        var fileReader = new FileReader(csvPathFile);
        List<Person> personList = new CsvToBeanBuilder<Person>(fileReader)
                .withType(Person.class)
                .build().parse();
        if (!personList.isEmpty()) {
            log.warn("Empty csv file {}", csvPathFile);
        }
        return personList;
    }

    public Message<Person> validateCsvFile(Message<Person> message) {
        return message;
    }
}
