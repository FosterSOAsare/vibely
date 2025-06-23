package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.CreatePostRequest;
import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.User;
import com.app.vibely.services.AuthService;
import com.app.vibely.services.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
@SuppressWarnings("unused")
public class PostsController {

    private final PostService postService;
    private final AuthService authService;

    // Get all posts with pagination
    @GetMapping
    public ResponseEntity<PagedResponse<PostDto>> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size
    ) {
        User user = authService.getCurrentUser();
        PagedResponse<PostDto> posts = postService.getAllPosts(page, size , user.getId() );
        return ResponseEntity.ok(posts);
    }

    // Get posts by user ID with optional start ID (for "load more") - Refactors waiting
    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedResponse<PostDto>> getPostsByUser(@PathVariable Integer userId, @RequestParam(required = false) Integer startId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size
    ) {
        PagedResponse<PostDto> posts = postService.getPostsByUser(userId, startId, page, size);
        return ResponseEntity.ok(posts);
    }

    // Delete post by ID
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        User user = authService.getCurrentUser();

        postService.deletePostById(postId , user.getId()) ;
        return ResponseEntity.ok(Map.of("message", "Postr deleted successfully"));
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
