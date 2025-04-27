package com.thehive.repositories;

import com.thehive.models.Message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByPropertyIdAndSenderIdOrReceiverId(String propertyId, String senderId, String receiverId);
    List<Message> findBySenderIdOrReceiverId(String senderId, String receiverId);
}
