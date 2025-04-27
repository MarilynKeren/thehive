package com.thehive.services;

import com.thehive.models.Message;

import com.thehive.models.User;
import com.thehive.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    
    private final MessageRepository messageRepository;
    
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    
    public Message sendMessage(User sender, String receiverId, String propertyId, String content) {
        Message message = new Message();
        message.setSenderId(sender.getId());
        message.setSenderName(sender.getFullName());
        message.setReceiverId(receiverId);
        message.setPropertyId(propertyId);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        message.setRead(false);
        
        return messageRepository.save(message);
    }
    
    public List<Message> getConversation(String propertyId, String userId1, String userId2) {
        return messageRepository.findByPropertyIdAndSenderIdOrReceiverId(propertyId, userId1, userId2);
    }
    
    public List<Message> getUserMessages(String userId) {
        return messageRepository.findBySenderIdOrReceiverId(userId, userId);
    }
    
    public void markAsRead(String messageId) {
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null) {
            message.setRead(true);
            messageRepository.save(message);
        }
    }
}
