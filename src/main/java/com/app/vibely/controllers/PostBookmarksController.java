package com.app.vibely.controllers;

import com.app.vibely.dtos.PostBookmarksDto;
import com.app.vibely.dtos.PostLikesDto;
import com.app.vibely.entities.Bookmark;
import com.app.vibely.entities.Like;
import com.app.vibely.mappers.PostBookmarksMapper;
import com.app.vibely.mappers.PostLikesMapper;
import com.app.vibely.services.PostBookmarksService;
import com.app.vibely.services.PostLikesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/{postId}/bookmarks")
@AllArgsConstructor
public class PostBookmarksController {
    private final PostBookmarksService postBookmarksService;
    private final PostBookmarksMapper postBookmarksMapper;
    private final PostLikesService postLikesService;
    private final PostLikesMapper likeMapper;

    // ✅ Toggle bookmark (save or unsave a post)
    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable Integer postId, Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        postBookmarksService.toggleBookmark(postId, userId);
        return ResponseEntity.ok(Map.of("message", "Bookmark toggled successfully"));
    }

    // ✅ Get paginated bookmarks on a post
    @GetMapping
    public ResponseEntity<List<PostBookmarksDto>> getPostLikes(@PathVariable Integer postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size
    ) {
        List<Bookmark> bookmarks = postBookmarksService.getBookmarksByPostId(postId, page, size);
        List<PostBookmarksDto> likeDtos = bookmarks.stream()
                .map(postBookmarksMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(likeDtos);
    }
}
