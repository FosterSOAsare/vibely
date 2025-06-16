package com.app.vibely.controllers;

import com.app.vibely.dtos.PostLikesDto;
import com.app.vibely.entities.Like;
import com.app.vibely.mappers.PostLikesMapper;
import com.app.vibely.services.PostLikesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
@AllArgsConstructor
public class PostLikesController {

    private final PostLikesService postLikesService;
    private final PostLikesMapper likeMapper;

    // ✅ Toggle like (like or unlike a post)
    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable Integer postId, Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        postLikesService.toggleLike(postId, userId);
        return ResponseEntity.ok(Map.of("message", "Like toggled successfully"));
    }

    // ✅ Get paginated likes on a post
    @GetMapping
    public ResponseEntity<List<PostLikesDto>> getPostLikes(@PathVariable Integer postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size
    ) {
        List<Like> likes = postLikesService.getLikesByPostId(postId, page, size);
        List<PostLikesDto> likeDtos = likes.stream()
                .map(likeMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(likeDtos);
    }
}
