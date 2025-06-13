package com.app.vibely.repositories;

import com.app.vibely.entities.EventComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCommentRepository extends JpaRepository<EventComment, Integer> {
}