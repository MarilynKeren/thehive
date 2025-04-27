package com.thehive.models;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payment {
    @Id
    private String id;
    
    private String bookingId;
    private double amount;
    private PaymentMethod method;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
    private String transactionReference;
    
    public enum PaymentMethod {
        BANK_TRANSFER, MOBILE_MONEY, CASH_ON_ARRIVAL, CREDIT_CARD
    }
    
    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED
    }
}