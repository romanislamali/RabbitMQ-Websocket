package com.sscl.websocket_service.controller;

import com.sscl.websocket_service.config.Paths;
import com.sscl.websocket_service.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(Paths.NOTIFICATION)
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PatchMapping(Paths.DELETE)
    public ResponseEntity<Map<String, Object>> softDeleteNotifications(@RequestBody List<UUID> ids) {
        String message = notificationService.deleteNotification(ids);
        Map<String, Object> response = Map.of("message", message);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(Paths.MARK_AS_READ)
    public ResponseEntity<Map<String, Object>> markMultipleAsRead(@RequestBody List<UUID> ids) {
        String message = notificationService.markNotificationsAsRead(ids);
        Map<String, Object> response = Map.of("message", message);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(Paths.COMMENT+Paths.MARK_AS_READ)
    public ResponseEntity<Map<String, Object>> markCommentsAsRead(@RequestBody List<UUID> ids) {
        String message = notificationService.markCommentsAsRead(ids);
        Map<String, Object> response = Map.of("message", message);
        return ResponseEntity.ok(response);
    }
}
