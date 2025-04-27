package com.thehive.models;

import lombok.AllArgsConstructor
;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    
    private String senderId; // User ID of sender
    private String senderName;
    private String receiverId; // User ID of receiver
    private String propertyId; // Property being discussed
    
    private String content;
    private LocalDateTime sentAt;
    private boolean isRead;
}