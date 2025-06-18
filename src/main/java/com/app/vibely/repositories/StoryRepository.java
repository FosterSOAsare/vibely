package com.app.vibely.repositories;

import com.app.vibely.entities.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findByUserIdOrderByCreatedAtDesc(Integer userId);
}