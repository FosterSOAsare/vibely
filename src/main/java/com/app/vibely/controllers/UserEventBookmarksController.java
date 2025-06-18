package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventsDto;
import com.app.vibely.services.UserEventBookmarksService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/events/bookmarks")
@AllArgsConstructor
@SuppressWarnings("unused")
public class UserEventBookmarksController {

    private final UserEventBookmarksService userEventBookmarksService;

    // ✅ Get paginated bookmarks of logged-in user
    @GetMapping
    public ResponseEntity<PagedResponse<EventsDto>> getMyBookmarks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        PagedResponse<EventsDto> bookmarks = userEventBookmarksService.getBookmarksByUserId(userId, page, size);
        return ResponseEntity.ok(bookmarks);
    }
}
