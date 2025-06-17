package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.PostLikesDto;
import com.app.vibely.services.PostLikesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
@AllArgsConstructor
@SuppressWarnings("unused")
public class PostLikesController {

    private final PostLikesService postLikesService;

    // ✅ Toggle like (like or unlike a post)
    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable Integer postId, Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        postLikesService.toggleLike(postId, userId);
        return ResponseEntity.ok(Map.of("message", "Like toggled successfully"));
    }

    // ✅ Get paginated likes on a post
    @GetMapping
    public ResponseEntity<PagedResponse<PostLikesDto>> getPostLikes(@PathVariable Integer postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        PagedResponse<PostLikesDto> likes = postLikesService.getLikesByPostId(postId, page, size);
        return ResponseEntity.ok(likes);
    }
}
