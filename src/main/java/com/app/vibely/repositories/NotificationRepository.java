package com.app.vibely.repositories;

import com.app.vibely.entities.Notification;
import com.app.vibely.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    
    // Find all notifications for a user
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    
    // Find unread notifications for a user
    List<Notification> findByUserAndIsReadFalseOrderByCreatedAtDesc(User user);
    
    // Delete all notifications for a user
    @Modifying
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.user = :user")
    void deleteByUser(@Param("user") User user);
    
    // Delete all notifications from the entire table
    @Modifying
    @Transactional
    @Query("DELETE FROM Notification")
    void deleteAllNotifications();
}