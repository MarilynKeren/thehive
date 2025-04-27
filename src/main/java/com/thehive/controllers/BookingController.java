package com.thehive.controllers;

import com.thehive.models.Booking;

import com.thehive.models.Property;
import com.thehive.models.User;
import com.thehive.services.BookingService;
import com.thehive.services.PropertyService;
import com.thehive.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    
    private final BookingService bookingService;
    private final PropertyService propertyService;
    private final UserService userService;
    
    public BookingController(BookingService bookingService, PropertyService propertyService, UserService userService) {
        this.bookingService = bookingService;
        this.propertyService = propertyService;
        this.userService = userService;
    }
    
    @PostMapping
    public String createBooking(
            @RequestParam String propertyId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam int numberOfGuests,
            Authentication authentication,
            Model model) {
        
        Property property = propertyService.getPropertyById(propertyId);
        if (property == null) {
            return "redirect:/properties";
        }
        
        User guest = null;
        if (authentication != null) {
            guest = userService.getUserById(authentication.getName());
        }
        
        Booking booking = new Booking();
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setNumberOfGuests(numberOfGuests);
        
        bookingService.createBooking(booking, property, guest);
        
        return "redirect:/bookings/" + booking.getId();
    }
    
    @GetMapping("/{id}")
    public String getBooking(@PathVariable String id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            return "redirect:/properties";
        }
        
        model.addAttribute("booking", booking);
        model.addAttribute("property", propertyService.getPropertyById(booking.getPropertyId()));
        return "bookings/detail";
    }
    
    @GetMapping("/guest")
    public String getGuestBookings(Authentication authentication, Model model) {
        String guestId = authentication.getName();
        model.addAttribute("bookings", bookingService.getBookingsByGuest(guestId));
        return "bookings/guest-list";
    }
    
    @GetMapping("/owner")
    public String getOwnerBookings(Authentication authentication, Model model) {
        String ownerId = authentication.getName();
        model.addAttribute("bookings", bookingService.getBookingsByOwner(ownerId));
        return "bookings/owner-list";
    }
    
    @PostMapping("/{id}/confirm")
    public String confirmBooking(@PathVariable String id) {
        bookingService.confirmBooking(id);
        return "redirect:/bookings/" + id;
    }
    
    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable String id) {
        bookingService.cancelBooking(id);
        return "redirect:/bookings/" + id;
    }
}