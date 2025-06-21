package com.app.vibely.repositories;

import com.app.vibely.entities.Story;
import com.app.vibely.entities.StoryView;
import com.app.vibely.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryViewRepository extends JpaRepository<StoryView, Integer> {
  boolean existsByStoryAndViewer(Story story, User viewer);
}