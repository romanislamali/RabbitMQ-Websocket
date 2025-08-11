package com.sscl.websocket_service.listener;

import com.sscl.websocket_service.config.Paths;
import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = Paths.NOTIFICATION_QUEUE)
    public void consume(NotificationDto dto) {
        notificationService.createAndSendNotification(dto);
    }
}
