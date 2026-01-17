package com.fastreserve.eventservice.mappers;

import com.fastreserve.eventservice.dto.EventRequest;
import com.fastreserve.eventservice.entities.Event;
import com.fastreserve.eventservice.entities.EventStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventMapper {

    private final ModelMapper modelMapper;

    public EventMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public Event toEntity(EventRequest request, String organizerEmail){
        if(request == null) return null;

        Event event = modelMapper.map(request, Event.class);
        event.setDateTime(LocalDateTime.parse(request.getDateTime()));

        event.setStatus(EventStatus.SCHEDULED);
        event.setOrganizerEmail(organizerEmail);

        return event;
    }

    public EventRequest toDTO(Event event){
        return modelMapper.map(event, EventRequest.class);
    }
}
