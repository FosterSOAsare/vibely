package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventsDto;
import com.app.vibely.services.UserLikedEventsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/events/likes")
@AllArgsConstructor
@SuppressWarnings("unused")
public class UserLikedEventsController {
    private final UserLikedEventsService userLikedEventsService;

    // ✅ Get paginated liked posts of logged-in user
    @GetMapping
    public ResponseEntity<PagedResponse<EventsDto>> getMyLikedPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        PagedResponse<EventsDto> bookmarks = userLikedEventsService.getLikedEventsByUserId(userId, page, size);
        return ResponseEntity.ok(bookmarks);
    }
}
