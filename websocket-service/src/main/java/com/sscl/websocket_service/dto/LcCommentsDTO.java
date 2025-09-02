package com.sscl.websocket_service.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class LcCommentsDTO {
    private UUID id;
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean isDeleted;
    private UUID lcId;
    private UUID makerId;
    private UUID checkerId;
    private String senderName;
    private String senderRole;
    private String sendingTime;
    private String receiverName;
    private String receiverRole;
    private String receivingTime;
    private UUID lcReferenceNo;
    private String comments;
    private String status;
    private String approvalStatus;
}
