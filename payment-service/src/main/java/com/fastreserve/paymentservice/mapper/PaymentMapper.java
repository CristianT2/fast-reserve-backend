package com.fastreserve.paymentservice.mapper;

import com.fastreserve.paymentservice.dto.PaymentRequest;
import com.fastreserve.paymentservice.dto.PaymentResponse;
import com.fastreserve.paymentservice.entity.Payment;
import com.fastreserve.paymentservice.entity.PaymentStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    private final ModelMapper modelMapper;

    public PaymentMapper(){
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public Payment toEntity(PaymentRequest request){
        if(request == null) return null;

        Payment payment = modelMapper.map(request, Payment.class);
        if(payment.getId() == null){
            payment.setStatus(PaymentStatus.PENDING);
        }

        return payment;
    }

    public PaymentResponse toDTO(Payment payment){
        if (payment == null) return null;
        PaymentResponse paymentDto = modelMapper.map(payment, PaymentResponse.class);

        if (payment.getStatus() != null) {
            paymentDto.setStatus(payment.getStatus().name());
        }

        return paymentDto;
    }
}
