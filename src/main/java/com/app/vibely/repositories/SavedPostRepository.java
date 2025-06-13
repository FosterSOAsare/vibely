package com.app.vibely.repositories;

import com.app.vibely.entities.SavedPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedPostRepository extends JpaRepository<SavedPost, Integer> {
}