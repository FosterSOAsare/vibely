package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.NotificationDto;
import com.app.vibely.entities.Notification;
import com.app.vibely.entities.User;
import com.app.vibely.mappers.NotificationMapper;
import com.app.vibely.repositories.NotificationRepository;
import com.app.vibely.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    // Create different types of notifications
    public Notification createLikeNotification(Integer postId, Integer likedByUserId, Integer postOwnerId) {
        if (likedByUserId.equals(postOwnerId)) {
            return null; // Don't notify users about their own actions
        }
        
        User postOwner = userRepository.findById(postOwnerId).orElseThrow();
        User likedByUser = userRepository.findById(likedByUserId).orElseThrow();
        
        return createNotification(
            postOwner,
            likedByUser.getUsername() + " liked your post",
            "LIKE",
            postId,
            "POST",
            likedByUser
        );
    }

    public Notification createCommentNotification(Integer postId, Integer commenterId, Integer postOwnerId) {
        if (commenterId.equals(postOwnerId)) {
            return null; // Don't notify users about their own actions
        }
        
        User postOwner = userRepository.findById(postOwnerId).orElseThrow();
        User commenter = userRepository.findById(commenterId).orElseThrow();
        
        return createNotification(
            postOwner,
            commenter.getUsername() + " commented on your post",
            "COMMENT",
            postId,
            "POST",
            commenter
        );
    }

    public Notification createFollowNotification(Integer followerId, Integer followedUserId) {
        User followedUser = userRepository.findById(followedUserId).orElseThrow();
        User follower = userRepository.findById(followerId).orElseThrow();
        
        return createNotification(
            followedUser,
            follower.getUsername() + " started following you",
            "FOLLOW",
            followerId,
            "USER",
            follower
        );
    }

    public Notification createEventNotification(Integer eventId, Integer eventCreatorId, Integer userId, String eventTitle) {
        if (eventCreatorId.equals(userId)) {
            return null; // Don't notify creator about their own event
        }
        
        User user = userRepository.findById(userId).orElseThrow();
        User eventCreator = userRepository.findById(eventCreatorId).orElseThrow();
        
        return createNotification(
            user,
            "New event: " + eventTitle,
            "EVENT",
            eventId,
            "EVENT",
            eventCreator
        );
    }

    // Generic notification creation method
    private Notification createNotification(User recipient, String message, String type, 
                                          Integer relatedEntityId, String relatedEntityType, User triggeredBy) {
        Notification notification = new Notification();
        notification.setUser(recipient);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRelatedEntityId(relatedEntityId);
        notification.setRelatedEntityType(relatedEntityType);
        notification.setTriggeredByUser(triggeredBy);
        notification.setIsRead(false);
        notification.setCreatedAt(Instant.now());
        
        return notificationRepository.save(notification);
    }

    // Get notifications for a user - now returns DTOs
    public List<NotificationDto> getNotificationsForUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return notificationRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    // Get unread notifications for a user - now returns DTOs
    public List<NotificationDto> getUnreadNotifications(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return notificationRepository.findByUserAndIsReadFalseOrderByCreatedAtDesc(user)
                .stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    // Get notifications by type - uses String like your other services
    public List<NotificationDto> getNotificationsByType(Integer userId, String type) {
        User user = userRepository.findById(userId).orElseThrow();
        return notificationRepository.findByUserAndTypeOrderByCreatedAtDesc(user, type)
                .stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    // Count unread notifications
    public long getUnreadCount(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return notificationRepository.countByUserAndIsReadFalse(user);
    }

    // Mark notification as read
    @Transactional
    public void markAsRead(Integer notificationId, Integer userId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        
        // Security check - only the owner can mark their notification as read
        if (!notification.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to mark this notification as read");
        }
        
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    // Mark all notifications as read for a user
    @Transactional
    public void markAllAsRead(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        notificationRepository.markAllAsReadForUser(user);
    }

    // Delete notification
    @Transactional
    public void deleteNotification(Integer notificationId, Integer userId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        
        // Security check - only the owner can delete their notification
        if (!notification.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to delete this notification");
        }
        
        notificationRepository.delete(notification);
    }

    // Get notifications with pagination - follows your exact pattern
    public PagedResponse<NotificationDto> getNotificationsWithPagination(Integer userId, int page, int size) {
        User user = userRepository.findById(userId).orElseThrow();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Notification> notificationsPage = notificationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        
        List<NotificationDto> dtos = notificationsPage.getContent()
                .stream()
                .map(notificationMapper::toDto)
                .toList();

        return new PagedResponse<>(dtos, page, size, notificationsPage.getTotalElements(), 
                                  notificationsPage.getTotalPages(), notificationsPage.hasNext(), 
                                  notificationsPage.hasPrevious());
    }
}