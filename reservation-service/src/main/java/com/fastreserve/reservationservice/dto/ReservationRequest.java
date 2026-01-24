package com.fastreserve.reservationservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {

    @NotNull(message = "El ID del evento es obligatorio")
    private Long eventId;

    @NotNull(message = "El número de asiento es obligatorio")
    private Integer seatNumber;
}
