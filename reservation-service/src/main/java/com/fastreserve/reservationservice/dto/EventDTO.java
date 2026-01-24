package com.fastreserve.reservationservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventDTO {

    private Long id;
    private String title;
    private BigDecimal price;
    private String status;
}
