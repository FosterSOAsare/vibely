package com.app.vibely.controllers;

import com.app.vibely.dtos.NotificationDto;
import com.app.vibely.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Get unread notifications for the logged-in user
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        List<NotificationDto> unreadNotifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(unreadNotifications);
    }

    // Mark a specific notification as read
    @PostMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<Map<String, Object>> markAsRead(
            @PathVariable Integer notificationId,
            Principal principal
    ) {
        Integer userId = Integer.parseInt(principal.getName());
        notificationService.markAsRead(notificationId, userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Notification marked as read");
        
        return ResponseEntity.ok(response);
    }
}