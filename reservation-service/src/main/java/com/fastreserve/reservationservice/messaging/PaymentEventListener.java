package com.fastreserve.reservationservice.messaging;

import com.fastreserve.reservationservice.service.interfaces.IReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

    private final IReservationService reservationService;

    @RabbitListener(queues = "payment_confirmation_queue")
    public void handlePaymentConfirmation(UUID reservationId){
        reservationService.confirmReservationPayment(reservationId);
    }
}
