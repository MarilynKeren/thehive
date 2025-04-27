package com.thehive.repositories;

import com.thehive.models.Property;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {
    List<Property> findByType(Property.PropertyType type);
    List<Property> findByLocation(String location);
    List<Property> findByOwnerId(String ownerId);
}
