package com.sscl.websocket_service.repository;

import com.sscl.websocket_service.entity.NotificationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRoleRepository extends JpaRepository<NotificationRole, UUID> {

}
