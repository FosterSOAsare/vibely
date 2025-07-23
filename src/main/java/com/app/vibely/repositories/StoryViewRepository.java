package com.app.vibely.repositories;

import com.app.vibely.dtos.StoryViewerDto;
import com.app.vibely.entities.Story;
import com.app.vibely.entities.StoryView;
import com.app.vibely.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryViewRepository extends JpaRepository<StoryView, Integer> {
  boolean existsByStoryAndViewer(Story story, User viewer);

  @Query("SELECT sv FROM StoryView sv WHERE sv.story = :story ORDER BY viewedAt DESC")
  List<StoryView> findViewersByStory(@Param("story") Story story);

  boolean existsByStoryIdAndViewerId(Integer storyId, Integer viewerId);
}