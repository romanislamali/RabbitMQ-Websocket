package com.sscl.websocket_service.service;

import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    void createAndSendNotification(NotificationDto dto);
    List<Notification> getAllRoleAndGroupBasedNotifications(String viewerRole, UUID groupId);
    List<Notification> getAllNotifications();
}
