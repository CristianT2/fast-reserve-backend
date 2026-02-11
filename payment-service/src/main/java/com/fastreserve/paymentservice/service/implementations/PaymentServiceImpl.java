package com.fastreserve.paymentservice.service.implementations;

import com.fastreserve.paymentservice.client.ReservationClient;
import com.fastreserve.paymentservice.config.RabbitMQConfig;
import com.fastreserve.paymentservice.dto.PaymentRequest;
import com.fastreserve.paymentservice.dto.PaymentResponse;
import com.fastreserve.paymentservice.dto.ReservationDTO;
import com.fastreserve.paymentservice.entity.Payment;
import com.fastreserve.paymentservice.entity.PaymentStatus;
import com.fastreserve.paymentservice.mapper.PaymentMapper;
import com.fastreserve.paymentservice.repository.PaymentRepository;
import com.fastreserve.paymentservice.service.interfaces.IPaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ReservationClient reservationClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Iniciando proceso de pago para la reserva: {}", request.getReservationId());
        ReservationDTO reservation = reservationClient.getReservationById(request.getReservationId());

        if(reservation == null){
            throw new RuntimeException("No se encontró la reserva con ID: " + request.getReservationId());
        }

        if(reservation.getTotalPrice().compareTo(request.getAmount()) != 0){
            log.error("El monto enviado ({}) no coincide con el de la reserva ({})",
                    request.getAmount(), reservation.getTotalPrice());
            throw new RuntimeException("El monto del pago no es correcto.");
        }

        Payment payment = paymentMapper.toEntity(request);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionReference("TRX-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Pago procesado exitosamente con ID: {}", savedPayment.getId());

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    savedPayment.getReservationId()
            );
            log.info("Mensaje enviado a RabbitMQ para confirmar reserva: {}", savedPayment.getReservationId());
        } catch (Exception e) {
            log.error("Error al enviar mensaje a RabbitMQ: {}", e.getMessage());
        }

        return paymentMapper.toDTO(savedPayment);
    }

    @Override
    @Transactional
    public PaymentResponse getPaymentByReservationId(UUID reservationId) {
        Payment payment = paymentRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado para la reserva: " + reservationId));
        return paymentMapper.toDTO(payment);
    }

    @Override
    @Transactional()
    public List<PaymentResponse> getAllPayments() {
        log.info("Obteniendo lista de todos los pagos");
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
