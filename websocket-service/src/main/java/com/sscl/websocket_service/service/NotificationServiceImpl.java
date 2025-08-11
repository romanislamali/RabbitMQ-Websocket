package com.sscl.websocket_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sscl.websocket_service.config.Paths;
import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.entity.Notification;
import com.sscl.websocket_service.repository.NotificationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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

        try {
            String jsonMessage = new ObjectMapper().writeValueAsString(dto);
            messagingTemplate.convertAndSend(Paths.TOPIC_ROLE + dto.getViewerRole(), jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Notification> getAllRoleBasedNotifications(String viewerRole) {
        return notificationRepository.findAllByViewerRole(viewerRole);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
