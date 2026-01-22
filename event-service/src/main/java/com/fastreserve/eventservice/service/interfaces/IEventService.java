package com.fastreserve.eventservice.service.interfaces;

import com.fastreserve.eventservice.dto.EventRequest;

import java.util.List;

public interface IEventService {

    EventRequest createEvent(EventRequest request, String organizerEmail);

    List<EventRequest> getAllEvents();

    List<EventRequest> getMyEvents(String email);

    EventRequest updateEvent(Long id, EventRequest request, String organizerEmail);

    void cancelEvent(Long id, String organizerEmail);
}
