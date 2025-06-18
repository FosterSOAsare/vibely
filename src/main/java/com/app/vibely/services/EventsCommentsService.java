package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventCommentsDto;
import com.app.vibely.entities.*;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.EventsCommentsMapper;
import com.app.vibely.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class EventsCommentsService {

    private final EventCommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventsCommentsMapper eventsCommentsMapper;

    // ✅ Create a comment
    @Transactional
    public EventComment createComment(Integer eventId, Integer userId, String text) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not authorized to perform this action"));

        EventComment comment = new EventComment();
        comment.setEvent(event);
        comment.setUser(user);
        comment.setText(text);
        comment.setCreatedAt(Instant.now());

        return commentRepository.save(comment);
    }

    // ✅ Delete a comment
    @Transactional
    public void deleteComment(Integer eventId, Integer commentId, Integer userId) {
        eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException( "Event not found"));
        EventComment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException( "Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new BadCredentialsException("User not authorized to perform this action");
        }

        commentRepository.deleteByIdAndEventId(commentId , eventId);
    }

    // ✅ Get comments of a post with pagination
    public PagedResponse<EventCommentsDto> getCommentsByEventId(Integer eventId, int page, int size) {
        // Ensure the post exists
        if (!eventRepository.existsById(eventId)) throw new ResourceNotFoundException("Event not found");

        Pageable pageable = PageRequest.of(page, size , Sort.by("id").descending());
        Page<EventComment> commentPage = commentRepository.findByEventId(eventId, pageable);
        List<EventCommentsDto> commentsDto = commentPage.stream().map(eventsCommentsMapper::toDto).toList();
        return new PagedResponse<>(commentsDto, page, size, commentPage.getTotalElements(), commentPage.getTotalPages(), commentPage.hasNext(), commentPage.hasPrevious());

    }

    public Integer calculateEventComments(Integer eventId) {
        return commentRepository.countByEventId(eventId);
    }

}
