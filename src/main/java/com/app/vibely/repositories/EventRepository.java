package com.app.vibely.repositories;

import com.app.vibely.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Page<Event> findByUserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);
    Page<Event> findByUserIdAndIdLessThanEqualOrderByCreatedAtDesc(Integer userId, Integer id, Pageable pageable);
    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);
}