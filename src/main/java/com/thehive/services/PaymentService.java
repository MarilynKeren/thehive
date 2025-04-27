package com.thehive.services;

import com.thehive.models.Booking;

import com.thehive.models.Payment;
import com.thehive.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    
    public Payment simulatePayment(Booking booking, Payment.PaymentMethod method) {
        Payment payment = new Payment();
        payment.setBookingId(booking.getId());
        payment.setAmount(booking.getTotalPrice());
        payment.setMethod(method);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionReference(UUID.randomUUID().toString());
        
        // Simulate payment processing
        if (method != Payment.PaymentMethod.CASH_ON_ARRIVAL) {
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
        } else {
            payment.setStatus(Payment.PaymentStatus.PENDING);
        }
        
        return paymentRepository.save(payment);
    }
    
    public List<Payment> getPaymentsByBooking(String bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }
    
    public Payment updatePaymentStatus(String paymentId, Payment.PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment != null) {
            payment.setStatus(status);
            return paymentRepository.save(payment);
        }
        return null;
    }
    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id).orElse(null);
    }
}
