package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventsBookmarksDto;


import com.app.vibely.services.EventsBookmarksService;
import com.app.vibely.services.PostBookmarksService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/events/{eventId}/bookmarks")
@AllArgsConstructor
@SuppressWarnings("unused")
public class EventBookmarksController {
    private final EventsBookmarksService eventsBookmarksService;
    private final PostBookmarksService postBookmarksService;

    // ✅ Toggle bookmark (save or un save an event)
    @PostMapping
    public ResponseEntity<?> toggleBookmark(@PathVariable Integer eventId, Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        eventsBookmarksService.toggleBookmark(eventId, userId);
        return ResponseEntity.ok(Map.of("message", "Bookmark toggled successfully"));
    }

    // ✅ Get paginated bookmarks on an event
    @GetMapping
    public ResponseEntity<PagedResponse<EventsBookmarksDto>> getPostBookmarks(@PathVariable Integer eventId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        PagedResponse<EventsBookmarksDto> bookmarks = eventsBookmarksService.getBookmarksByEventId(eventId, page, size);
        return ResponseEntity.ok(bookmarks);
    }
}
