package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.CreatePostCommentRequest;
import com.app.vibely.dtos.PostCommentsDto;
import com.app.vibely.entities.Comment;
import com.app.vibely.entities.User;
import com.app.vibely.services.AuthService;
import com.app.vibely.services.PostCommentsService;
import com.app.vibely.mappers.PostCommentsMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/posts/{postId}/comments")
@SuppressWarnings("unused")
public class PostCommentsController {

    private final PostCommentsService postCommentsService;
    private final PostCommentsMapper commentMapper;
    private final AuthService authService;

    // ✅ Create a comment
    @PostMapping("")
    public ResponseEntity<PostCommentsDto> createComment(@Valid @RequestBody CreatePostCommentRequest request , @PathVariable Integer postId) {

       User user = authService.getCurrentUser();
       Comment comment = postCommentsService.createComment(postId, user.getId(), request.getText());
       return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toDto(comment));
    }

    // 📥 Get comments by post ID
    @GetMapping("")
    public ResponseEntity<PagedResponse<PostCommentsDto>> getCommentsByPostId(@PathVariable Integer postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        PagedResponse<PostCommentsDto> comments = postCommentsService.getCommentsByPostId(postId, page, size);
        return ResponseEntity.ok(comments);
    }


    // ❌ Delete a comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
        User user = authService.getCurrentUser();
        postCommentsService.deleteComment(commentId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
