package com.app.vibely.controllers;

import com.app.vibely.services.EventCommentLikesService;
import com.app.vibely.services.PostCommentLikesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/events/{eventId}/comments/{commentId}/likes")
@AllArgsConstructor
@SuppressWarnings("unused")
public class EventCommentLikesController {
    private final EventCommentLikesService commentLikesService;
    // ✅ Toggle like (like or unlike an event comment)
    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable Integer eventId, @PathVariable Integer commentId, Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        commentLikesService.toggleCommentLike(eventId, commentId, userId);
        return ResponseEntity.ok(Map.of("message", "Like toggled successfully"));
    }
}
