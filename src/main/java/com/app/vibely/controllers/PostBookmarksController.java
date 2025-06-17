package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.PostBookmarksDto;


import com.app.vibely.services.PostBookmarksService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/bookmarks")
@AllArgsConstructor
@SuppressWarnings("unused")
public class PostBookmarksController {
    private final PostBookmarksService postBookmarksService;

    // ✅ Toggle bookmark (save or unsave a post)
    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable Integer postId, Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        postBookmarksService.toggleBookmark(postId, userId);
        return ResponseEntity.ok(Map.of("message", "Bookmark toggled successfully"));
    }

    // ✅ Get paginated bookmarks on a post
    @GetMapping
    public ResponseEntity<PagedResponse<PostBookmarksDto>> getPostLikes(@PathVariable Integer postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        PagedResponse<PostBookmarksDto> bookmarks = postBookmarksService.getBookmarksByPostId(postId, page, size);
        return ResponseEntity.ok(bookmarks);
    }
}
