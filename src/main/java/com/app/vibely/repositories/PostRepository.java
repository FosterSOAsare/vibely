package com.app.vibely.repositories;

import com.app.vibely.entities.Post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByUserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);
    Page<Post> findByUserIdAndIdLessThanEqualOrderByCreatedAtDesc(Integer userId, Integer id, Pageable pageable);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}