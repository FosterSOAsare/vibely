package com.app.vibely.controllers;

import com.app.vibely.dtos.PostCommentsDto;
import com.app.vibely.entities.Comment;
import com.app.vibely.entities.Post;
import com.app.vibely.entities.User;
import com.app.vibely.services.AuthService;
import com.app.vibely.services.PostCommentsService;
import com.app.vibely.mappers.PostCommentsMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class PostCommentsController {

    private final PostCommentsService postCommentsService;
    private final PostCommentsMapper commentMapper;
    private final AuthService authService;

    // ✅ Create a comment
    @PostMapping("")
    public ResponseEntity<PostCommentsDto> createComment(@PathVariable Integer postId, @RequestBody PostCommentsDto commentDto) {

       User user = authService.getCurrentUser();
        Comment comment = postCommentsService.createComment(postId, user.getId(), commentDto.getText());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toDto(comment));
    }

    // 📥 Get comments by post ID
    @GetMapping
    public ResponseEntity<List<PostCommentsDto>> getCommentsByPostId(@PathVariable Integer postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        List<Comment> comments = postCommentsService.getCommentsByPostId(postId, page, size);
        List<PostCommentsDto> commentDtos = comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentDtos);
    }


    // ❌ Delete a comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
        User user = authService.getCurrentUser();
        postCommentsService.deleteComment(commentId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
