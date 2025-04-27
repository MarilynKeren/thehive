package com.thehive.controllers;

import com.thehive.models.Booking;

import com.thehive.models.Property;
import com.thehive.models.User;
import com.thehive.services.BookingService;
import com.thehive.services.PropertyService;
import com.thehive.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final UserService userService;
    private final PropertyService propertyService;
    private final BookingService bookingService;
    
    public AdminController(UserService userService, PropertyService propertyService, BookingService bookingService) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.bookingService = bookingService;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "admin/dashboard";
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users/list";
    }
    
    @GetMapping("/properties")
    public String listProperties(Model model) {
        List<Property> properties = propertyService.getAllProperties();
        model.addAttribute("properties", properties);
        return "admin/properties/list";
    }
    
    @GetMapping("/bookings")
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin/bookings/list";
    }
}