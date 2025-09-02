package com.sscl.websocket_service.controller;

import com.sscl.websocket_service.config.Paths;
import com.sscl.websocket_service.entity.LcCommentForNotification;
import com.sscl.websocket_service.entity.Notification;
import com.sscl.websocket_service.service.NotificationService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class NotificationFetchController {

    private final NotificationService notificationService;

    public NotificationFetchController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping(Paths.FETCH_ALL_NOTIFICATIONS + Paths.VIEWER_ROLE + Paths.GROUP_ID)
    @SendToUser(Paths.QUEUE_ALL_NOTIFICATIONS)
    public List<Notification> fetchAllNotifications(@DestinationVariable String viewerRole, @DestinationVariable UUID groupId) {
        return notificationService.getAllRoleAndGroupBasedNotificationsForCustomer(viewerRole, groupId);
    }

    @MessageMapping(Paths.FETCH_ALL_NOTIFICATIONS + Paths.VIEWER_ROLE)
    @SendToUser(Paths.QUEUE_ALL_NOTIFICATIONS)
    public List<Notification> fetchAllNotificationsBank(@DestinationVariable String viewerRole) {
        return notificationService.getAllRoleBasedNotificationsForBank(viewerRole);
    }

    @MessageMapping(Paths.FETCH_ALL_COMMENTS + Paths.LC_ID)
    @SendToUser(Paths.QUEUE_ALL_COMMENTS)
    public List<LcCommentForNotification> fetchAllCommentsByLcId(@DestinationVariable UUID lcId) {
        return notificationService.getAllCommentsByLcId(lcId);
    }
}
