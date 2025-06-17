package com.app.vibely.repositories;

import com.app.vibely.entities.EventComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCommentRepository extends JpaRepository<EventComment, Integer> {
    Page<EventComment> findByEventId(Integer eventId, Pageable pageable);
    Integer countByEventId(Integer eventId);
    void deleteByIdAndEventId(Integer commentId, Integer eventId);
}