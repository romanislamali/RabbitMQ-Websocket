package com.sscl.websocket_service.controller;

import com.sscl.websocket_service.config.Paths;
import com.sscl.websocket_service.entity.Notification;
import com.sscl.websocket_service.service.NotificationService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping(Paths.FETCH_ALL_NOTIFICATIONS + Paths.VIEWER_ROLE)
    @SendToUser(Paths.QUEUE_ALL_NOTIFICATIONS)
    public List<Notification> fetchAllNotifications(@DestinationVariable String viewerRole) {
        return notificationService.getAllRoleBasedNotifications(viewerRole);
    }
}
