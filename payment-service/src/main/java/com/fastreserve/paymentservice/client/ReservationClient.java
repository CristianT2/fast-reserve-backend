package com.fastreserve.paymentservice.client;

import com.fastreserve.paymentservice.dto.ReservationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "reservation-service", url = "http://localhost:8083")
public interface ReservationClient {

    @GetMapping("/api/reservations/{id}")
    ReservationDTO getReservationById(@PathVariable("id") UUID id);
}
