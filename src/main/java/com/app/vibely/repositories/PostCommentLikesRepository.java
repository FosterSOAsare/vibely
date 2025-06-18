package com.app.vibely.repositories;

import com.app.vibely.entities.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCommentLikesRepository extends JpaRepository<CommentLike, Integer> {
    Optional<CommentLike> findByCommentIdAndUserId(Integer commentId, Integer userId);

}
