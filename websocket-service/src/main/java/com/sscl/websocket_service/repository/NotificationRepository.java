package com.sscl.websocket_service.repository;

import com.sscl.websocket_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query("SELECT n FROM Notification n JOIN NotificationRole nr ON n.id = nr.notificationId " +
            "WHERE nr.roleType = :roleType AND n.groupId = :groupId AND n.isDeleted = false " +
            "ORDER BY n.createdAt DESC")
    List<Notification> findAllByRoleTypeAndGroup(@Param("roleType") String roleType,
                                                 @Param("groupId") UUID groupId);

    @Query("SELECT n FROM Notification n JOIN NotificationRole nr ON n.id = nr.notificationId " +
            "WHERE nr.roleType = :roleType AND n.isDeleted = false " +
            "ORDER BY n.createdAt DESC")
    List<Notification> findAllByRoleType(@Param("roleType") String roleType);
}
