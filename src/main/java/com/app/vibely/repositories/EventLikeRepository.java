package com.app.vibely.repositories;

import com.app.vibely.entities.EventLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLikeRepository extends JpaRepository<EventLike, Integer> {
    EventLike findByEventIdAndUserId(Integer postId, Integer userId);
    Page<EventLike> findByEventId(Integer eventId, Pageable pageable);
    Integer countByEventId(Integer eventId);
    Page<EventLike> findByUserId(Integer userId , Pageable pageable);
}