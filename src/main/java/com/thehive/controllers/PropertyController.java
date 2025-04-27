package com.thehive.controllers;

import com.thehive.models.Property;

import com.thehive.services.PropertyService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/properties")
public class PropertyController {
    
    private final PropertyService propertyService;
    
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
    
    @GetMapping
    public String getAllProperties(Model model) {
        model.addAttribute("properties", propertyService.getAllProperties());
        return "properties/list";
    }
    
    @GetMapping("/{id}")
    public String getPropertyById(@PathVariable String id, Model model) {
        Property property = propertyService.getPropertyById(id);
        if (property == null) {
            return "redirect:/properties";
        }
        model.addAttribute("property", property);
        return "properties/detail";
    }
    
    @GetMapping("/search")
    public String searchProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            Model model) {
        
        List<Property> properties;
        if (location != null && !location.isEmpty()) {
            properties = propertyService.getPropertiesByLocation(location);
        } else if (type != null && !type.isEmpty()) {
            properties = propertyService.getPropertiesByType(Property.PropertyType.valueOf(type.toUpperCase()));
        } else {
            properties = propertyService.getAllProperties();
        }
        
        model.addAttribute("properties", properties);
        return "properties/list";
    }
    
    @GetMapping("/owner")
    public String getOwnerProperties(Authentication authentication, Model model) {
        String ownerId = authentication.getName(); // Assuming username is the ID
        model.addAttribute("properties", propertyService.getPropertiesByOwner(ownerId));
        return "properties/owner-list";
    }
}