package com.sscl.websocket_service.service;

import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.entity.Notification;

import java.util.List;

public interface NotificationService {
    void createAndSendNotification(NotificationDto dto);
    List<Notification> getAllRoleBasedNotifications(String viewerRole);
    List<Notification> getAllNotifications();
}
