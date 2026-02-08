package com.fastreserve.paymentservice.controller;

import com.fastreserve.paymentservice.dto.PaymentRequest;
import com.fastreserve.paymentservice.dto.PaymentResponse;
import com.fastreserve.paymentservice.service.interfaces.IPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request){
        return new ResponseEntity<>(paymentService.processPayment(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayment(){
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<PaymentResponse> getPaymentByReservationId(@PathVariable UUID reservationId){
        return ResponseEntity.ok(paymentService.getPaymentByReservationId(reservationId));
    }
}
