package com.sscl.websocket_service.service;

import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.entity.LcCommentForNotification;
import com.sscl.websocket_service.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    void createAndSendNotification(NotificationDto dto);
    List<Notification> getAllRoleAndGroupBasedNotificationsForCustomer(String viewerRole, UUID groupId);
    List<Notification> getAllRoleBasedNotificationsForBank(String viewerRole);
    String deleteNotification(List<UUID> ids);
    String markNotificationsAsRead(List<UUID> ids);

    List<LcCommentForNotification> getAllCommentsByLcId(UUID lcId);
    String markCommentsAsRead(List<UUID> ids);
}
