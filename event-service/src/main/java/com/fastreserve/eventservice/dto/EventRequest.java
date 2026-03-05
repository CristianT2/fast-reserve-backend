package com.fastreserve.eventservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequest {

    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "La fecha y hora son obligatorias")
    private String dateTime; // Formato: 2026-05-20T18:30:00

    @NotBlank(message = "La ubicación es obligatoria")
    private String location;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima debe ser 1")
    private Integer capacity;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private BigDecimal price;

    private String status;
}
