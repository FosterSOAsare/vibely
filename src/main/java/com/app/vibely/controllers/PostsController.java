package com.app.vibely.controllers;

import com.app.vibely.dtos.CreatePostRequest;
import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.Post;
import com.app.vibely.entities.User;
import com.app.vibely.services.AuthService;
import com.app.vibely.services.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;
    private final AuthService authService;

    // Get all posts with pagination
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<PostDto> posts = postService.getAllPosts(page, size);
        return ResponseEntity.ok(posts);
    }

    // Get posts by user ID with optional start ID (for "load more")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(
            @PathVariable Integer userId,
            @RequestParam(required = false) Integer startId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<PostDto> posts = postService.getPostsByUser(userId, Optional.ofNullable(startId), page, size);
        return ResponseEntity.ok(posts);
    }

    // Delete post by ID
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest request) {
        User user = authService.getCurrentUser();
        PostDto postDto = postService.createPost(request, user.getId());
        postDto.setIsLiked(false);
        postDto.setIsSaved(false);
        return ResponseEntity.ok(postDto);
    }


}
