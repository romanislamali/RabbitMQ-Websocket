package com.sscl.websocket_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sscl.websocket_service.config.Paths;
import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.entity.Notification;
import com.sscl.websocket_service.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void createAndSendNotification(NotificationDto dto) {
        Notification notification = Notification.builder()
                .message(dto.getMessage())
                .viewerRole(dto.getViewerRole())
                .lcId(dto.getLcId())
                .groupId(dto.getGroupId())
                .lcStatus(dto.getLcStatus())
                .createdBy(dto.getCreatedBy())
                .isRead(false)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        notificationRepository.save(notification);

        sendNotificationToWebSocket(dto);
    }

    private void sendNotificationToWebSocket(NotificationDto dto) {
        try {
            messagingTemplate.convertAndSend(
                    Paths.TOPIC_ROLE + dto.getViewerRole(),
                    objectMapper.writeValueAsString(dto)
            );
        } catch (JsonProcessingException e) {
            log.error("Failed to send WebSocket notification for LC ID: {}", dto.getLcId(), e);
        }
    }

    @Override
    public List<Notification> getAllRoleAndGroupBasedNotificationsForCustomer(String viewerRole, UUID groupId) {
        return notificationRepository.findAllByViewerRoleAndGroupIdAndIsDeletedFalseOrderByCreatedAtDesc(viewerRole, groupId);
    }

    @Override
    public List<Notification> getAllRoleBasedNotificationsForBank(String viewerRole) {
        return notificationRepository.findAllByViewerRoleAndIsDeletedFalseOrderByCreatedAtDesc(viewerRole);
    }

    @Override
    @Transactional
    public String deleteNotification(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return "No notifications to delete";
        }

        List<Notification> notificationList = notificationRepository.findAllById(ids);

        if (notificationList.isEmpty()) {
            return "No notifications found for the provided IDs";
        }

        notificationList.forEach(notification -> notification.setDeleted(true));
        notificationRepository.saveAll(notificationList);

        return "Soft deleted " + notificationList.size() + " notification(s) successfully";
    }

    @Override
    @Transactional
    public String markNotificationsAsRead(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return "No notifications to mark as read";
        }

        List<Notification> notifications = notificationRepository.findAllById(ids);

        if (notifications.isEmpty()) {
            return "No notifications found for the provided IDs";
        }

        notifications.forEach(n -> n.setIsRead(true));

        notificationRepository.saveAll(notifications);

        return "Marked " + notifications.size() + " notification(s) as read successfully";
    }
}
