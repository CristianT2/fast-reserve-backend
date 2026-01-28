package com.fastreserve.reservationservice.service.interfaces;

import com.fastreserve.reservationservice.dto.ReservationRequest;
import com.fastreserve.reservationservice.dto.ReservationResponse;

import java.util.List;
import java.util.UUID;

public interface IReservationService {

    ReservationResponse createReservation(ReservationRequest request, String token);
    List<ReservationResponse> getReservationsByUserId(Long userId);
    ReservationResponse getReservationById(UUID id);
}
