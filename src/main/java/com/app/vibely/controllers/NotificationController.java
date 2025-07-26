package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.NotificationDto;
import com.app.vibely.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Get all notifications for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotifications(@PathVariable Integer userId) {
        List<NotificationDto> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }

    // Get unread notifications for a user
    @GetMapping("/{userId}/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(@PathVariable Integer userId) {
        List<NotificationDto> unreadNotifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(unreadNotifications);
    }

    // Get notifications by type
    @GetMapping("/{userId}/type/{type}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByType(
            @PathVariable Integer userId,
            @PathVariable String type
    ) {
        List<NotificationDto> notificationsByType = notificationService.getNotificationsByType(userId, type);
        return ResponseEntity.ok(notificationsByType);
    }

    // Count unread notifications
    @GetMapping("/{userId}/count-unread")
    public ResponseEntity<Long> countUnreadNotifications(@PathVariable Integer userId) {
        long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(count);
    }

    // Mark a specific notification as read
    @PostMapping("/{userId}/mark-as-read/{notificationId}")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Integer userId,
            @PathVariable Integer notificationId
    ) {
        notificationService.markAsRead(notificationId, userId);
        return ResponseEntity.ok().build();
    }

    // Mark all notifications as read
    @PostMapping("/{userId}/mark-all-as-read")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Integer userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    // Delete a notification
    @DeleteMapping("/{userId}/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Integer userId,
            @PathVariable Integer notificationId
    ) {
        notificationService.deleteNotification(notificationId, userId);
        return ResponseEntity.ok().build();
    }

    // Get notifications with pagination
    @GetMapping("/{userId}/paged")
    public ResponseEntity<PagedResponse<NotificationDto>> getNotificationsPaged(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<NotificationDto> pagedNotifications =
                notificationService.getNotificationsWithPagination(userId, page, size);
        return ResponseEntity.ok(pagedNotifications);
    }
}