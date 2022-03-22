package com.adme.learning.cloudstream.mappers;

import com.adme.learning.cloudstream.entities.EventEntity;
import com.adme.learning.cloudstream.models.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventEntity modelToEntity(Event model);

    Event entityToModel(EventEntity entity);
}
