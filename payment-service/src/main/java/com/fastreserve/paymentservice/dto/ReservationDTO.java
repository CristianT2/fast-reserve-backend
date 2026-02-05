package com.fastreserve.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {

    private UUID id;
    private BigDecimal totalPrice;
    private String status;
    private Long userId;
}
