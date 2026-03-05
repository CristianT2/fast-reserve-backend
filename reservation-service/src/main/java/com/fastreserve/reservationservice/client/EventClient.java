package com.fastreserve.reservationservice.client;


import com.fastreserve.reservationservice.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service", url = "http://localhost:8080")
public interface EventClient {

    @GetMapping("/api/events/{id}")
    EventDTO getEventById(@PathVariable("id") Long id);
}
