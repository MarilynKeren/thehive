package com.thehive.controllers;

import com.thehive.models.Booking;

import com.thehive.models.Payment;
import com.thehive.services.BookingService;
import com.thehive.services.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {
    
    private final PaymentService paymentService;
    private final BookingService bookingService;
    
    public PaymentController(PaymentService paymentService, BookingService bookingService) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
    }
    
    @PostMapping
    public String createPayment(
            @RequestParam String bookingId,
            @RequestParam String paymentMethod) {
        
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return "redirect:/bookings";
        }
        
        Payment.PaymentMethod method = Payment.PaymentMethod.valueOf(paymentMethod.toUpperCase());
        paymentService.simulatePayment(booking, method);
        
        return "redirect:/bookings/" + bookingId;
    }
    
    @GetMapping("/{id}")
    public String getPayment(@PathVariable String id, Model model) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) {
            return "redirect:/bookings";
        }
        
        model.addAttribute("payment", payment);
        model.addAttribute("booking", bookingService.getBookingById(payment.getBookingId()));
        return "payments/detail";
    }
}
