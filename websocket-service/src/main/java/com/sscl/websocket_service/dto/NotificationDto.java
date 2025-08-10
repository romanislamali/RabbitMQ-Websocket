package com.sscl.websocket_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto implements Serializable {
    private String message;
    private String viewerRole;
    private UUID lcId;
    private String lcStatus;
    private String createdBy;
}

