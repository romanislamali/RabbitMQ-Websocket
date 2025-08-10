package com.sscl.websocket_service.service;

import com.sscl.websocket_service.dto.NotificationDto;

public interface NotificationService {
    void createAndSendNotification(NotificationDto dto);
}
