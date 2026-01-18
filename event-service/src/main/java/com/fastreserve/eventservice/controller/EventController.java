package com.fastreserve.eventservice.controller;

import com.fastreserve.eventservice.dto.EventRequest;
import com.fastreserve.eventservice.service.interfaces.IEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final IEventService eventService;

    @PostMapping
    public ResponseEntity<EventRequest> createEvent(@Valid @RequestBody EventRequest eventRequest){
        log.info("REST request to create Event: {}", eventRequest.getTitle());
        String email = getCurrentUserEmail();
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(eventRequest, email));
    }

    @GetMapping
    public ResponseEntity<List<EventRequest>> getAllEvents(){
        log.info("REST request to get all Events");
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<EventRequest>> getMyEvents(){
        String email = getCurrentUserEmail();
        log.info("REST request to get Events for user: {}", email);
        return ResponseEntity.ok(eventService.getMyEvents(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventRequest> updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequest eventRequest){
        log.info("REST request to update Event ID: {}", id);
        String email = getCurrentUserEmail();
        return ResponseEntity.ok(eventService.updateEvent(id, eventRequest, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventRequest> cancelEvent(@PathVariable Long id){
        log.info("REST request to cancel Event ID: {}", id);
        String email = getCurrentUserEmail();
        eventService.cancelEvent(id, email);
        return ResponseEntity.noContent().build();
    }

    private String getCurrentUserEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
