package com.app.vibely.repositories;

import com.app.vibely.entities.EventCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventCommentLikesRepository extends JpaRepository<EventCommentLike, Integer> {
    Optional<EventCommentLike> findByCommentIdAndUserId(Integer commentId, Integer userId);

}
