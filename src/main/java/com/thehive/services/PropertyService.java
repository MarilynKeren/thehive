package com.thehive.services;

import com.thehive.models.Property;

import com.thehive.models.User;
import com.thehive.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    
    private final PropertyRepository propertyRepository;
    
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    
    public Property addProperty(Property property, User owner) {
        property.setOwnerId(owner.getId());
        return propertyRepository.save(property);
    }
    
    public Property updateProperty(Property property) {
        return propertyRepository.save(property);
    }
    
    public void deleteProperty(String id) {
        propertyRepository.deleteById(id);
    }
    
    public Property getPropertyById(String id) {
        return propertyRepository.findById(id).orElse(null);
    }
    
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
    
    public List<Property> getPropertiesByType(Property.PropertyType type) {
        return propertyRepository.findByType(type);
    }
    
    public List<Property> getPropertiesByLocation(String location) {
        return propertyRepository.findByLocation(location);
    }
    
    public List<Property> getPropertiesByOwner(String ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }
}