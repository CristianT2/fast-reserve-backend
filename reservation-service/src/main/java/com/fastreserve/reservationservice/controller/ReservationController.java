package com.fastreserve.reservationservice.controller;

import com.fastreserve.reservationservice.dto.ReservationRequest;
import com.fastreserve.reservationservice.dto.ReservationResponse;
import com.fastreserve.reservationservice.service.interfaces.IReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request,
                                                                 @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(reservationService.createReservation(request, token), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponse>> getReservationByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }
}
