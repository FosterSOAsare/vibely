package com.app.vibely.repositories;

import com.app.vibely.entities.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Integer> {
}