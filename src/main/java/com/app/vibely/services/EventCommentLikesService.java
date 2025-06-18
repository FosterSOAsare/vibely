package com.app.vibely.services;

import com.app.vibely.entities.*;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventCommentLikesService {
    private final EventCommentLikesRepository likeRepository;
    private final EventCommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    // ✅ Add or remove like (toggle)
    @Transactional
    public void toggleCommentLike(Integer postId, Integer commentId , Integer userId) {
        Event event = eventRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        EventComment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException( "User not authorized to perform this action"));

        Optional<EventCommentLike> existingLike = likeRepository.findByCommentIdAndUserId(commentId, userId);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get()); // Remove like (unlike)
        } else {
            EventCommentLike like = new EventCommentLike();
            like.setComment(comment);
            like.setUser(user);
            like.setCreatedAt(Instant.now());
            likeRepository.save(like);
        }
    }
}
