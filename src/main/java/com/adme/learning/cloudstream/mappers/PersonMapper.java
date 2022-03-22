package com.adme.learning.cloudstream.mappers;

import com.adme.learning.cloudstream.entities.PersonEntity;
import com.adme.learning.cloudstream.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE= Mappers.getMapper(PersonMapper.class);

    PersonEntity modelToEntity(Person model);
    Person entityToModel(PersonEntity entity);
}
