package com.app.vibely.repositories;

import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.Post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);
    List<Post> findByUserIdAndIdLessThanOrderByCreatedAtDesc(Integer userId, Integer id, Pageable pageable);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}