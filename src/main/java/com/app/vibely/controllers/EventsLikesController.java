package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventsLikesDto;
import com.app.vibely.services.EventsLikesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/events/{eventId}/likes")
@AllArgsConstructor
@SuppressWarnings("unused")
public class EventsLikesController {

    private final EventsLikesService eventsLikesService;

    // ✅ Toggle like (like or unlike an event)
    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable Integer eventId, Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        eventsLikesService.toggleLike(eventId, userId);
        return ResponseEntity.ok(Map.of("message", "Like toggled successfully"));
    }

    // ✅ Get paginated likes on an event
    @GetMapping
    public ResponseEntity<PagedResponse<EventsLikesDto>> getEventLikes(@PathVariable Integer eventId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        PagedResponse<EventsLikesDto> likes = eventsLikesService.getLikesByEventId(eventId, page, size);
        return ResponseEntity.ok(likes);
    }
}
