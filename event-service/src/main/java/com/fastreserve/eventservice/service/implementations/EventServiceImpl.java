package com.fastreserve.eventservice.service.implementations;

import com.fastreserve.eventservice.dto.EventRequest;
import com.fastreserve.eventservice.entities.Event;
import com.fastreserve.eventservice.entities.EventStatus;
import com.fastreserve.eventservice.mappers.EventMapper;
import com.fastreserve.eventservice.repository.EventRepository;
import com.fastreserve.eventservice.service.interfaces.IEventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Transactional
    @Override
    public EventRequest createEvent(EventRequest request, String organizerEmail) {
        try{
            LocalDateTime eventDate = LocalDateTime.parse(request.getDateTime());
            if (eventRepository.existsByTitleAndDateTimeAndOrganizerEmail(
                    request.getTitle(), eventDate, organizerEmail)){
                throw new RuntimeException("Ya has creado un evento con este título para la misma fecha");
            }

            Event event = eventMapper.toEntity(request, organizerEmail);
            Event savedEvent = eventRepository.save(event);

            return eventMapper.toDTO(savedEvent);
        } catch (Exception e) {
            throw new RuntimeException("Error en la creación del evento");
        }
    }

    @Override
    public List<EventRequest> getAllEvents() {
        try{
            return eventRepository.findAll().stream()
                    .map(eventMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los eventos");
        }
    }

    @Override
    public List<EventRequest> getMyEvents(String email) {
        try{
            return eventRepository.findByOrganizerEmail(email).stream()
                    .map(eventMapper :: toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los eventos");
        }
    }

    @Transactional
    @Override
    public EventRequest updateEvent(Long id, EventRequest request, String organizerEmail) {
        try{
            Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
            if (!event.getOrganizerEmail().equals(organizerEmail)){
                throw new RuntimeException("No autorizado");
            }

            event.setTitle(request.getTitle());
            event.setDescription(request.getDescription());
            event.setLocation(request.getLocation());
            event.setCapacity(request.getCapacity());
            event.setPrice(request.getPrice());

            return eventMapper.toDTO(eventRepository.save(event));
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el evento");
        }
    }

    @Transactional
    @Override
    public void cancelEvent(Long id, String organizerEmail) {
        try {
            Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
            if (!event.getOrganizerEmail().equals(organizerEmail)){
                throw new RuntimeException("No autorizado");
            }

            event.setStatus(EventStatus.CANCELLED);
            eventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Error al cancelar el evento");
        }
    }

    @Override
    public EventRequest getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el evento con ID "+ id));
        return eventMapper.toDTO(event);
    }
}
