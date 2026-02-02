package com.fastreserve.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private UUID id;
    private Long userId;
    private Long eventId;
    private Integer seatNumber;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime expirationDate;
}
