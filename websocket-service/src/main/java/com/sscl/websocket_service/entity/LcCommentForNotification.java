package com.sscl.websocket_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "lc_comment_for_notification")
public class LcCommentForNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "lc_id", nullable = false)
    private UUID lcId;

    @Column(name = "maker_id")
    private UUID makerId;

    @Column(name = "checkerId")
    private UUID checkerId;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "sender_role")
    private String senderRole;

    @Column(name = "sending_time")
    private String sendingTime;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_role")
    private String receiverRole;

    @Column(name = "receiving_time")
    private String receivingTime;

    @Column(name = "lc_reference_no")
    private UUID lcReferenceNo;

    @Column(name = "comments", columnDefinition = "text")
    private String comments;

    @Column(name = "status")
    private String status;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "is_read")
    private Boolean isRead = false;
}
