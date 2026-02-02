package com.fastreserve.reservationservice.repository;

import com.fastreserve.reservationservice.entities.Reservation;
import com.fastreserve.reservationservice.entities.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByStatusAndExpirationDate(ReservationStatus status, LocalDateTime now);
    boolean existsByEventIdAndSeatNumberAndStatus(Long eventId, Integer seatNumber, ReservationStatus status);
    boolean existsByUserIdAndEventIdAndStatus(Long userId, Long EventId, ReservationStatus status);
}
