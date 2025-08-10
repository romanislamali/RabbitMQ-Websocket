package com.sscl.websocket_service.controller;

import com.sscl.websocket_service.dto.NotificationDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class NotificationRestController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("/notify")
    public String send(@RequestBody NotificationDto message) {
        rabbitTemplate.convertAndSend("notification.exchange", "notify.lc", message);
        return "Message queued";
    }
}

