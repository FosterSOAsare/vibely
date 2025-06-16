package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.CreatePostRequest;
import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.Post;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.DuplicateUserException;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.services.AuthService;
import com.app.vibely.services.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;
    private final AuthService authService;

    // Get all posts with pagination
    @GetMapping
    public ResponseEntity<PagedResponse<PostDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        User user = authService.getCurrentUser();
        PagedResponse<PostDto> posts = postService.getAllPosts(page, size , user.getId());
        return ResponseEntity.ok(posts);
    }

    // Get posts by user ID with optional start ID (for "load more") - Refactors waiting
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
        User user = authService.getCurrentUser();

        postService.deletePostById(postId , user.getId()) ;
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
