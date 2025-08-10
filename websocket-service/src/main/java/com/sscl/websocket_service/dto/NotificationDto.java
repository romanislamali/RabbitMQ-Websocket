package com.sscl.websocket_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class NotificationDto implements Serializable {
    private String message;
    private String role;

    public NotificationDto() {}
}

