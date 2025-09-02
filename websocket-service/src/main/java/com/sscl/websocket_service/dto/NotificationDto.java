package com.sscl.websocket_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto implements Serializable {
    private UUID id;
    private String message;
    private List<String> viewerRoles;
    private UUID lcId;
    private UUID groupId;
    private String lcStatus;
    private String createdBy;
    private String updatedBy;
    private String updatedAt;
    private String isDeleted;
    private Boolean isRead;
    private LcCommentsDTO lcComment;
}

