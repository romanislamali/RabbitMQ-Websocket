package com.sscl.websocket_service.repository;

import com.sscl.websocket_service.entity.LcCommentForNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LcCommentsRepository extends JpaRepository<LcCommentForNotification, UUID> {
    List<LcCommentForNotification> findByLcIdAndIsDeletedFalseOrderByCreatedAtDesc(UUID lcId);
}
