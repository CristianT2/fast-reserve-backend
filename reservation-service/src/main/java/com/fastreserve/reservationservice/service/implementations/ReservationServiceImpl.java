package com.fastreserve.reservationservice.service.implementations;

import com.fastreserve.reservationservice.client.EventClient;
import com.fastreserve.reservationservice.config.JwtService;
import com.fastreserve.reservationservice.dto.EventDTO;
import com.fastreserve.reservationservice.dto.ReservationRequest;
import com.fastreserve.reservationservice.dto.ReservationResponse;
import com.fastreserve.reservationservice.entities.Reservation;
import com.fastreserve.reservationservice.mappers.ReservationMapper;
import com.fastreserve.reservationservice.repository.ReservationRepository;
import com.fastreserve.reservationservice.service.interfaces.IReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final EventClient eventClient;
    private final StringRedisTemplate redisTemplate;
    private final JwtService jwtService;

    @Override
    @Transactional
    public ReservationResponse createReservation(ReservationRequest request, String token) {

        EventDTO event = eventClient.getEventById(request.getEventId());
        if(event == null) {
            throw new RuntimeException("El evento no existe");
        }

        String lockKey = "lock:event" + request.getEventId() + ":seat:" + request.getSeatNumber();
        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(lockKey, "RESERVED", Duration.ofMinutes(10));

        if (Boolean.FALSE.equals(isLocked)){
            throw new RuntimeException("El asiento " + request.getSeatNumber() + " ya esta reservado por otra persona");
        }

        String pureToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        Long userId = jwtService.extractUserId(pureToken);

        Reservation reservation = reservationMapper.toEntity(
                request,
                userId,
                event.getPrice()
        );

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toDTO(savedReservation);
    }

    @Override
    public List<ReservationResponse> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    @Override
    public ReservationResponse getReservationById(UUID id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
    }
}
