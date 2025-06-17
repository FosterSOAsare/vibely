package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.PostDto;
import com.app.vibely.services.UserLikedPostsServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts/likes")
@AllArgsConstructor
@SuppressWarnings("unused")
public class UserLikedPostsController {
    private final UserLikedPostsServices userLikedPostsServices;

    // ✅ Get paginated liked posts of logged-in user
    @GetMapping
    public ResponseEntity<PagedResponse<PostDto>> getMyLikedPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        PagedResponse<PostDto> bookmarks = userLikedPostsServices.getLikedPostsByUserId(userId, page, size);
        return ResponseEntity.ok(bookmarks);
    }
}
