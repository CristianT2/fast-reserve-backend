package com.fastreserve.paymentservice.service.interfaces;

import com.fastreserve.paymentservice.dto.PaymentRequest;
import com.fastreserve.paymentservice.dto.PaymentResponse;


import java.util.List;
import java.util.UUID;

public interface IPaymentService {

    PaymentResponse processPayment(PaymentRequest request);
    PaymentResponse getPaymentByReservationId(UUID reservationId);
    List<PaymentResponse> getAllPayments();
}
