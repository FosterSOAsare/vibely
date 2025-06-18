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
public class PostCommentLikesService {
    private final PostCommentLikesRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // ✅ Add or remove like (toggle)
    @Transactional
    public void toggleCommentLike(Integer postId, Integer commentId , Integer userId) {
        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException( "User not authorized to perform this action"));

        Optional<CommentLike> existingLike = likeRepository.findByCommentIdAndUserId(commentId, userId);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get()); // Remove like (unlike)
        } else {
            CommentLike like = new CommentLike();
            like.setComment(comment);
            like.setUser(user);
            like.setCreatedAt(Instant.now());
            likeRepository.save(like);
        }
    }
}
