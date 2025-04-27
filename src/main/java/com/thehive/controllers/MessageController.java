package com.thehive.controllers;

import com.thehive.models.Message;

import com.thehive.models.Property;
import com.thehive.models.User;
import com.thehive.services.MessageService;
import com.thehive.services.PropertyService;
import com.thehive.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {
    
    private final MessageService messageService;
    private final UserService userService;
    private final PropertyService propertyService;
    
    public MessageController(MessageService messageService, UserService userService, PropertyService propertyService) {
        this.messageService = messageService;
        this.userService = userService;
        this.propertyService = propertyService;
    }
    
    @PostMapping
    public String sendMessage(
            @RequestParam String propertyId,
            @RequestParam String receiverId,
            @RequestParam String content,
            Authentication authentication) {
        
        User sender = userService.getUserById(authentication.getName());
        messageService.sendMessage(sender, receiverId, propertyId, content);
        
        return "redirect:/messages?propertyId=" + propertyId + "&userId=" + receiverId;
    }
    
    @GetMapping
    public String getConversation(
            @RequestParam String propertyId,
            @RequestParam String userId,
            Authentication authentication,
            Model model) {
        
        String currentUserId = authentication.getName();
        List<Message> messages = messageService.getConversation(propertyId, currentUserId, userId);
        
        model.addAttribute("messages", messages);
        model.addAttribute("property", propertyService.getPropertyById(propertyId));
        model.addAttribute("otherUser", userService.getUserById(userId));
        
        return "messages/conversation";
    }
    
    @GetMapping("/inbox")
    public String getInbox(Authentication authentication, Model model) {
        String userId = authentication.getName();
        List<Message> messages = messageService.getUserMessages(userId);
        
        model.addAttribute("messages", messages);
        return "messages/inbox";
    }
}