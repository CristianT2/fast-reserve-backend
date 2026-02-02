package com.fastreserve.reservationservice.mappers;

import com.fastreserve.reservationservice.dto.ReservationRequest;
import com.fastreserve.reservationservice.dto.ReservationResponse;
import com.fastreserve.reservationservice.entities.Reservation;
import com.fastreserve.reservationservice.entities.ReservationStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ReservationMapper {

    private final ModelMapper modelMapper;


    public ReservationMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public Reservation toEntity(ReservationRequest request, Long userId, BigDecimal price){

        if(request == null) return null;
        //BigDecimal finalPrice = (price != null) ? BigDecimal.valueOf(price) : BigDecimal.ZERO;

        return Reservation.builder()
                .userId(userId)
                .eventId(request.getEventId())
                .totalPrice(price)
                .seatNumber(request.getSeatNumber())
                .reservationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusMinutes(10))
                .status(ReservationStatus.PENDING_PAYMENT)
                .build();
    }

    public ReservationResponse toDTO(Reservation reservation){
        if (reservation == null) return null;

        return ReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .eventId(reservation.getEventId())
                .seatNumber(reservation.getSeatNumber())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus().name())
                .expirationDate(reservation.getExpirationDate())
                .build();
    }
}
