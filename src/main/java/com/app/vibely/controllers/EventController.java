package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.CreateEventRequest;
import com.app.vibely.dtos.EventsDto;
import com.app.vibely.services.EventsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
@SuppressWarnings("unused")
public class EventController {
    private final EventsService eventsService;

    // Get all posts with pagination
    @GetMapping("")
    public ResponseEntity<PagedResponse<EventsDto>> getAllEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size , Principal principal) {
        Integer userId  = Integer.parseInt(principal.getName());
        PagedResponse<EventsDto> posts = eventsService.getAllEvents(page, size , userId );
        return ResponseEntity.ok(posts);
    }

    // Get events by user ID with optional start ID (for "load more") - Refactors waiting
    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedResponse<EventsDto>> getPostsByUser(@PathVariable Integer userId, @RequestParam(required = false) Integer startId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size
    ) {
        PagedResponse<EventsDto> posts = eventsService.getPostsByUser(userId, startId, page, size);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("")
    public ResponseEntity<EventsDto> createEvent(@Valid @RequestBody CreateEventRequest request , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        EventsDto eventDto = eventsService.createEvent(request, userId);
        eventDto.setIsLiked(false);
        eventDto.setIsSaved(false);
        return ResponseEntity.ok(eventDto);
    }

    // Delete event by ID
    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer eventId , Principal principal) {
        Integer userId  = Integer.parseInt(principal.getName());

        eventsService.deleteEventById(eventId , userId) ;
        return ResponseEntity.noContent().build();
    }
}
