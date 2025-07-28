package com.app.vibely.services;

import com.app.vibely.dtos.NotificationDto;
import com.app.vibely.entities.Notification;
import com.app.vibely.entities.User;
import com.app.vibely.mappers.NotificationMapper;
import com.app.vibely.repositories.NotificationRepository;
import com.app.vibely.repositories.UserRepository;
import lombok.AllArgsConstructor;
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
            "/account/user/[username]/posts?postId=" + postId + "&userId=" + postOwnerId + "&username=" + postOwner.getUsername()
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
            "/account/user/[username]/posts?postId=" + postId + "&userId=" + postOwnerId + "&username=" + postOwner.getUsername()
        );
    }

    public Notification createFollowNotification(Integer followerId, Integer followedUserId) {
        User followedUser = userRepository.findById(followedUserId).orElseThrow();
        User follower = userRepository.findById(followerId).orElseThrow();
        
        return createNotification(
            followedUser,
            follower.getUsername() + " started following you",
            "/account/user/[username]/followers?userId=" + followedUserId + "&username=" + followedUser.getUsername()
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
            "/events?eventId=" + eventId + "&userId=" + eventCreatorId + "&username=" + eventCreator.getUsername()
        );
    }

    public Notification createEventLikeNotification(Integer eventId, Integer likedByUserId, Integer eventOwnerId) {
        if (likedByUserId.equals(eventOwnerId)) {
            return null; // Don't notify users about their own actions
        }

        User eventOwner = userRepository.findById(eventOwnerId).orElseThrow();
        User likedByUser = userRepository.findById(likedByUserId).orElseThrow();

        return createNotification(
                eventOwner,
                likedByUser.getUsername() + " liked your event",
                "/events?eventId=" + eventId + "&userId=" + eventOwnerId + "&username=" + eventOwner.getUsername()
        );
    }

    public Notification createEventCommentNotification(Integer eventId, Integer commenterId, Integer eventOwnerId) {
        if (commenterId.equals(eventOwnerId)) {
            return null; // Don't notify users about their own actions
        }

        User eventOwner = userRepository.findById(eventOwnerId).orElseThrow();
        User commenter = userRepository.findById(commenterId).orElseThrow();

        return createNotification(
                eventOwner,
                commenter.getUsername() + " commented on your event",
                "/events?eventId=" + eventId + "&userId=" + eventOwnerId + "&username=" + eventOwner.getUsername()
        );
    }

    // Generic notification creation method
    private Notification createNotification(User recipient, String message, String link) {
        Notification notification = new Notification();
        notification.setUser(recipient);
        notification.setMessage(message);
        notification.setLink(link);
        notification.setIsRead(false);
        notification.setCreatedAt(Instant.now());
        
        return notificationRepository.save(notification);
    }

    // Delete all notifications from the entire table
    @Transactional
    public void deleteAllNotifications() {
        notificationRepository.deleteAllNotifications();
    }

    // Get unread notifications for a user and delete all notifications
    @Transactional
    public List<NotificationDto> getUnreadNotifications(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        
        // Get unread notifications first
        List<NotificationDto> unreadNotifications = notificationRepository.findByUserAndIsReadFalseOrderByCreatedAtDesc(user).stream().map(notificationMapper::toDto).toList();
        return unreadNotifications;
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
}