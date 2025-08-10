package com.sscl.websocket_service.service;

import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.entity.Notification;
import com.sscl.websocket_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void createAndSendNotification(NotificationDto dto) {
        System.out.println("notification :"+dto);
        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setRole(dto.getRole());
        notification.setCreatedAt(Instant.now());

        Notification savedNotification = notificationRepository.save(notification);
        System.out.println("savedNotification :"+savedNotification);

        // Send to frontend WebSocket
        messagingTemplate.convertAndSend("/topic/role/" + dto.getRole(), dto);
    }
}
