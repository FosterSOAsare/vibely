package com.app.vibely.repositories;

import com.app.vibely.entities.Notification;
import com.app.vibely.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    
    // Find notifications for a specific user
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    
    // Find notifications for a specific user with pagination
    Page<Notification> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    
    // Find unread notifications for a user
    List<Notification> findByUserAndIsReadFalseOrderByCreatedAtDesc(User user);
    
    // Find notifications by type for a user
    List<Notification> findByUserAndTypeOrderByCreatedAtDesc(User user, String type);
    
    // Find notifications triggered by a specific user
    List<Notification> findByTriggeredByUserOrderByCreatedAtDesc(User triggeredByUser);
    
    // Find notifications related to a specific entity
    List<Notification> findByRelatedEntityTypeAndRelatedEntityIdOrderByCreatedAtDesc(
            String relatedEntityType, Integer relatedEntityId);
    
    // Count unread notifications for a user
    long countByUserAndIsReadFalse(User user);
    
    // Mark all notifications as read for a user
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user = :user AND n.isRead = false")
    void markAllAsReadForUser(@Param("user") User user);
    
    // Find notifications for a user with pagination support
    @Query("SELECT n FROM Notification n WHERE n.user = :user ORDER BY n.createdAt DESC")
    List<Notification> findNotificationsForUser(@Param("user") User user);
}