package com.sscl.websocket_service.controller;

import com.sscl.websocket_service.config.Paths;
import com.sscl.websocket_service.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Paths.NOTIFICATION)
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PatchMapping(Paths.DELETE)
    public ResponseEntity<String> softDeleteNotifications(@RequestBody List<UUID> ids) {
        String message = notificationService.deleteNotification(ids);
        return ResponseEntity.ok(message);
    }

    @PatchMapping(Paths.MARK_AS_READ)
    public ResponseEntity<String> markMultipleAsRead(@RequestBody List<UUID> ids) {
        String message = notificationService.markNotificationsAsRead(ids);
        return ResponseEntity.ok(message);
    }
}
