package com.fastreserve.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private Long id;
    private String title;
    private BigDecimal price;
    private String status;
}
