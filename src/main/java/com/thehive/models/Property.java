package com.thehive.models;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "properties")
public class Property {
    @Id
    private String id;
    
    private String name;
    private String description;
    private PropertyType type;
    private String location;
    private double pricePerNight;
    private int capacity;
    private List<String> amenities;
    private List<String> imageUrls;
    private String ownerId; // Reference to User who owns this property
    
    public enum PropertyType {
        HOTEL, MOTEL, GUESTHOUSE, APARTMENT, VILLA
    }
}