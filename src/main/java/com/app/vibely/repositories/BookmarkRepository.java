package com.app.vibely.repositories;

import com.app.vibely.entities.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    Bookmark findByPostIdAndUserId(Integer postId, Integer userId);
    Page<Bookmark> findByPostId(Integer postId, Pageable pageable);
    Page<Bookmark> findByUserId(Integer userId , Pageable pageable);

}