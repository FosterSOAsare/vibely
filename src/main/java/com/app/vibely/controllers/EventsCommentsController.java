package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.CreateCommentRequest;
import com.app.vibely.dtos.EventCommentsDto;
import com.app.vibely.entities.EventComment;
import com.app.vibely.mappers.EventsCommentsMapper;
import com.app.vibely.services.AuthService;
import com.app.vibely.services.EventsCommentsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@AllArgsConstructor
@RestController
@RequestMapping("/api/events/{eventId}/comments")
@SuppressWarnings("unused")
public class EventsCommentsController {

    private final EventsCommentsService eventsCommentsService;
    private final EventsCommentsMapper commentMapper;
    private final AuthService authService;

    // ✅ Create a comment
    @PostMapping("")
    public ResponseEntity<EventCommentsDto> createComment(@Valid @RequestBody CreateCommentRequest request , @PathVariable Integer eventId , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        EventComment comment = eventsCommentsService.createComment(eventId, userId, request.getText());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toDto(comment));
    }

    // 📥 Get comments by post ID
    @GetMapping("")
    public ResponseEntity<PagedResponse<EventCommentsDto>> getCommentsByEventId(@PathVariable Integer eventId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        PagedResponse<EventCommentsDto> comments = eventsCommentsService.getCommentsByEventId(eventId, page, size);
        return ResponseEntity.ok(comments);
    }


    // ❌ Delete a comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer eventId ,@PathVariable Integer commentId , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        eventsCommentsService.deleteComment(eventId , commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
