package com.thehive.repositories;

import com.thehive.models.Payment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByBookingId(String bookingId);
}