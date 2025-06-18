package com.app.vibely.repositories;

import com.app.vibely.entities.Story;
import com.app.vibely.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findByUserIdOrderByCreatedAtDesc(Integer userId);

    @Query("SELECT DISTINCT s.user FROM Story s")
    List<User> findUsersWithStories();

}