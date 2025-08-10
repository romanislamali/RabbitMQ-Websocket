package com.sscl.websocket_service.listener;

import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "notification.queue")
    public void consume(NotificationDto dto) {
        System.out.println("✅ Received notification from RabbitMQ: " + dto);
        notificationService.createAndSendNotification(dto);
    }
}
