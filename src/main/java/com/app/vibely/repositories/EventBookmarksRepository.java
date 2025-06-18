package com.app.vibely.repositories;

import com.app.vibely.entities.EventBookmarks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventBookmarksRepository  extends JpaRepository<EventBookmarks, Integer> {
    EventBookmarks findByEventIdAndUserId(Integer eventId, Integer userId);
    Page<EventBookmarks> findByEventId(Integer eventId, Pageable pageable);
    Page<EventBookmarks> findByUserId(Integer userId , Pageable pageable);
}
