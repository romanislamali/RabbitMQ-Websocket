package com.sscl.websocket_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sscl.websocket_service.config.Paths;
import com.sscl.websocket_service.dto.LcCommentsDTO;
import com.sscl.websocket_service.dto.NotificationDto;
import com.sscl.websocket_service.entity.LcCommentForNotification;
import com.sscl.websocket_service.entity.Notification;
import com.sscl.websocket_service.entity.NotificationRole;
import com.sscl.websocket_service.repository.LcCommentsRepository;
import com.sscl.websocket_service.repository.NotificationRepository;
import com.sscl.websocket_service.repository.NotificationRoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final NotificationRoleRepository notificationRoleRepository;
    private final LcCommentsRepository lcCommentsRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationRoleRepository notificationRoleRepository, LcCommentsRepository lcCommentsRepository, SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationRoleRepository = notificationRoleRepository;
        this.lcCommentsRepository = lcCommentsRepository;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void createAndSendNotification(NotificationDto dto) {

        Notification notification = Notification.builder()
                .message(dto.getMessage())
                .lcId(dto.getLcId())
                .groupId(dto.getGroupId())
                .lcStatus(dto.getLcStatus())
                .createdBy(dto.getCreatedBy())
                .isDeleted(false)
                .isRead(false)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        dto.setId(savedNotification.getId());

        // Save NotificationRole for each viewerRole
        for (String roleType : dto.getViewerRoles()) {
            NotificationRole nr = new NotificationRole();
            nr.setNotificationId(savedNotification.getId());
            nr.setRoleType(roleType);
            notificationRoleRepository.save(nr);
        }


        LcCommentForNotification lcCommentForNotification = lcCommentsRepository.save(convertLcCommentDtoToEntity(dto.getLcComment()));

        // Broadcast notification to each role via WebSocket
        sendNotificationToWebSocket(dto);

        // Broadcast comment
        sendCommentToWebSocket(lcCommentForNotification);
    }

    private void sendNotificationToWebSocket(NotificationDto dto) {
        try {
            for (String roleType : dto.getViewerRoles()) {
                messagingTemplate.convertAndSend(
                        Paths.TOPIC_ROLE + roleType,
                        objectMapper.writeValueAsString(dto)
                );
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to send WebSocket notification for LC ID: {}", dto.getLcId(), e);
        }
    }

    private void sendCommentToWebSocket(LcCommentForNotification comment) {
        try {
                messagingTemplate.convertAndSend(
                        Paths.TOPIC_COMMENTS + comment.getLcId(),
                        objectMapper.writeValueAsString(comment)
                );
        } catch (JsonProcessingException e) {
            log.error("Failed to send WebSocket comment for LC ID: {}", comment.getLcId(), e);
        }
    }


    private LcCommentForNotification convertLcCommentDtoToEntity(LcCommentsDTO dto){
        LcCommentForNotification commentForNotification = new LcCommentForNotification();
        commentForNotification.setLcId(dto.getLcId());
        commentForNotification.setMakerId(dto.getMakerId());
        commentForNotification.setCheckerId(dto.getCheckerId());
        commentForNotification.setSenderName(dto.getSenderName());
        commentForNotification.setSenderRole(dto.getSenderRole());
        commentForNotification.setSendingTime(dto.getSendingTime());
        commentForNotification.setReceiverName(dto.getReceiverName());
        commentForNotification.setReceiverRole(dto.getReceiverRole());
        commentForNotification.setReceivingTime(dto.getReceivingTime());
        commentForNotification.setLcReferenceNo(dto.getLcReferenceNo());
        commentForNotification.setComments(dto.getComments());
        commentForNotification.setStatus(dto.getStatus());
        commentForNotification.setApprovalStatus(dto.getApprovalStatus());
        commentForNotification.setCreatedAt(dto.getCreatedAt());
        commentForNotification.setCreatedBy(dto.getCreatedBy());
        commentForNotification.setUpdatedAt(dto.getUpdatedAt());
        commentForNotification.setUpdatedBy(dto.getUpdatedBy());
        commentForNotification.setDeleted(dto.isDeleted());
        commentForNotification.setIsRead(false);
        return commentForNotification;
    }

    @Transactional
    public String deleteNotification(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return "No notifications to delete";
        }

        List<Notification> notificationList = notificationRepository.findAllById(ids);

        if (notificationList.isEmpty()) {
            return "No notifications found for the provided IDs";
        }

        notificationList.forEach(notification -> notification.setDeleted(true));
        notificationRepository.saveAll(notificationList);

        return "Soft deleted " + notificationList.size() + " notification(s) successfully";
    }

    @Override
    @Transactional
    public String markNotificationsAsRead(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return "No notifications to mark as read";
        }

        List<Notification> notifications = notificationRepository.findAllById(ids);

        if (notifications.isEmpty()) {
            return "No notifications found for the provided IDs";
        }

        notifications.forEach(n -> n.setIsRead(true));

        notificationRepository.saveAll(notifications);

        return "Marked " + notifications.size() + " notification(s) as read successfully";
    }

    @Override
    public List<Notification> getAllRoleAndGroupBasedNotificationsForCustomer(String roleType, UUID groupId) {
        return notificationRepository.findAllByRoleTypeAndGroup(roleType, groupId);
    }

    @Override
    public List<Notification> getAllRoleBasedNotificationsForBank(String roleType) {
        return notificationRepository.findAllByRoleType(roleType);
    }

    @Override
    public List<LcCommentForNotification> getAllCommentsByLcId(UUID lcId) {
        return lcCommentsRepository.findByLcIdAndIsDeletedFalseOrderByCreatedAtDesc(lcId);
    }

    @Override
    @Transactional
    public String markCommentsAsRead(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return "No comments to mark as read";
        }

        List<LcCommentForNotification> comments = lcCommentsRepository.findAllById(ids);

        if (comments.isEmpty()) {
            return "No comments found for the provided IDs";
        }

        comments.forEach(c -> c.setIsRead(true));

        lcCommentsRepository.saveAll(comments);

        return "Marked " + comments.size() + " comments as read successfully";
    }
}
