package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.PostDto;
import com.app.vibely.services.UserPostBookmarksService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts/bookmarks")
@AllArgsConstructor
@SuppressWarnings("unused")
public class UserPostBookmarksController {

    private final UserPostBookmarksService userPostBookmarksService;

    // ✅ Get paginated bookmarks of logged-in user
    @GetMapping
    public ResponseEntity<PagedResponse<PostDto>> getMyBookmarks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        PagedResponse<PostDto> bookmarks = userPostBookmarksService.getBookmarksByUserId(userId, page, size);
        return ResponseEntity.ok(bookmarks);
    }
}
