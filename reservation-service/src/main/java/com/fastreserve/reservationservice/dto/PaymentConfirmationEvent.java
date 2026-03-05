package com.fastreserve.reservationservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentConfirmationEvent {

    private UUID reservationId;
}
