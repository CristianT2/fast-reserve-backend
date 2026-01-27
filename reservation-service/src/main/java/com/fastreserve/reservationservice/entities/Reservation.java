package com.fastreserve.reservationservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long userId;
    private Long eventId;
    private LocalDateTime reservationDate;
    private LocalDateTime expirationDate;
    private Integer seatNumber;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
