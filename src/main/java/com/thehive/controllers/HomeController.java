package com.thehive.controllers;

import com.thehive.models.Property;

import com.thehive.services.PropertyService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {
    
    private final PropertyService propertyService;
    
    public HomeController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
    
    @GetMapping("/")
    public String home(Model model, Principal principal) {
        // Remove property listing for unauthenticated users
        if (principal != null) {
            model.addAttribute("properties", propertyService.getAllProperties());
        }
        return "home"; // Now just shows role selection
    }
    @GetMapping("/select-role")
    public String handleRoleSelection(
        @RequestParam String role,
        HttpSession session) {
        
        // Store selected role in session for login page
        session.setAttribute("selectedRole", role);
        
        return switch (role.toUpperCase()) {
            case "GUEST" -> "redirect:/properties";
            case "OWNER", "ADMIN" -> "redirect:/login";
            default -> "redirect:/?error=invalid_role";
        };
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        // In a real app, you would check the user's role and redirect accordingly
        return "redirect:/owner/dashboard";
    }
}