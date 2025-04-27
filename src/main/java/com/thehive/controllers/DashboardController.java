package com.thehive.controllers;

import com.thehive.models.User;

import com.thehive.services.BookingService;
import com.thehive.services.PropertyService;
import com.thehive.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final PropertyService propertyService;
    private final BookingService bookingService;
    private final UserService userService;

    public DashboardController(PropertyService propertyService, 
                             BookingService bookingService,
                             UserService userService) {
        this.propertyService = propertyService;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/owner/dashboard")
    public String ownerDashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("properties", propertyService.getPropertiesByOwner(user.getId()));
        model.addAttribute("bookings", bookingService.getBookingsByOwner(user.getId()));
        model.addAttribute("user", userService.getUserById(user.getId()));
        return "owner/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("properties", propertyService.getAllProperties());
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("user", user);
        return "admin/dashboard";
    }
}