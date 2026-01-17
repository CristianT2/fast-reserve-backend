package com.fastreserve.eventservice.repository;

import com.fastreserve.eventservice.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByOrganizerEmail(String email);
    boolean existsByTitleAndDateTimeAndOrganizerEmail(String title, LocalDateTime dateTime, String organizerEmail);
}
