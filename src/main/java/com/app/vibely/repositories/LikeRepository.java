package com.app.vibely.repositories;

import com.app.vibely.entities.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Like findByPostIdAndUserId(Integer postId, Integer userId);
    Page<Like> findByPostId(Integer postId, Pageable pageable);
    Integer countByPostId(Integer postId);
}