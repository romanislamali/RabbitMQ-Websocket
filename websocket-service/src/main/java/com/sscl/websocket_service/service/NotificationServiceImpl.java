package com.sscl.websocket_service.service;

import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.entity.Notification;
import com.sscl.websocket_service.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void createAndSendNotification(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setViewerRole(dto.getViewerRole());
        notification.setLcId(dto.getLcId());
        notification.setLcStatus(dto.getLcStatus());
        notification.setCreatedBy(dto.getCreatedBy());
        notification.setIsRead(false);
        notification.setCreatedAt(Instant.now());
        notification.setUpdatedAt(Instant.now());

        Notification savedNotification = notificationRepository.save(notification);

        // Send to frontend WebSocket
        messagingTemplate.convertAndSend("/topic/role/" + dto.getViewerRole(), dto);
    }
}
