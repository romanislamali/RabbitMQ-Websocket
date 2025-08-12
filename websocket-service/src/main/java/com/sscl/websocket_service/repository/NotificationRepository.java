package com.sscl.websocket_service.repository;

import com.sscl.websocket_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByViewerRoleAndGroupId(String viewerRole, UUID groupId);
    List<Notification> findByViewerRoleAndIsReadFalse(String role);
}
