package com.thehive.services;

import com.thehive.models.Booking;

import com.thehive.models.Property;
import com.thehive.models.User;
import com.thehive.repositories.BookingRepository;
import com.thehive.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    
    public BookingService(BookingRepository bookingRepository, PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }
    
    public Booking createBooking(Booking booking, Property property, User guest) {
        // Calculate total price
        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        double totalPrice = nights * property.getPricePerNight();
        
        booking.setTotalPrice(totalPrice);
        booking.setPropertyId(property.getId());
        
        if (guest != null) {
            booking.setGuestId(guest.getId());
            booking.setGuestName(guest.getFullName());
            booking.setGuestEmail(guest.getEmail());
            booking.setGuestPhone(guest.getPhone());
        }
        
        booking.setStatus(Booking.BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }
    
    public Booking confirmBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setStatus(Booking.BookingStatus.CONFIRMED);
            return bookingRepository.save(booking);
        }
        return null;
    }
    
    public Booking cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
            return bookingRepository.save(booking);
        }
        return null;
    }
    
    public List<Booking> getBookingsByProperty(String propertyId) {
        return bookingRepository.findByPropertyId(propertyId);
    }
    
    public List<Booking> getBookingsByGuest(String guestId) {
        return bookingRepository.findByGuestId(guestId);
    }
    
    public List<Booking> getBookingsByOwner(String ownerId) {
        return bookingRepository.findByOwnerId(ownerId);
    }
    public Booking getBookingById(String id) {
        return bookingRepository.findById(id).orElse(null);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
