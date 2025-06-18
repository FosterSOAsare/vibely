package com.app.vibely.controllers;

import com.app.vibely.services.PostCommentLikesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/comments/{commentId}/likes")
@AllArgsConstructor
@SuppressWarnings("unused")
public class PostCommentLikesController {

    private final PostCommentLikesService commentLikesService;
    // ✅ Toggle like (like or unlike a post comment)
    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable Integer postId, @PathVariable Integer commentId    , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        commentLikesService.toggleCommentLike(postId, commentId, userId);
        return ResponseEntity.ok(Map.of("message", "Like toggled successfully"));
    }
}
