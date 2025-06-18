package com.app.vibely.repositories;

import com.app.vibely.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    // Who follows a specific user (followers of X)
    Page<Follow> findAllByFollowing_Id(Integer userId, Pageable pageable);

    @Query("SELECT COUNT(f) > 0 FROM Follow f WHERE f.follower.id = :followerId AND f.following.id = :userId")
    boolean checkIfUserIsFollowed(@Param("userId") Integer userId, @Param("followerId") Integer followerId);


    @Query("SELECT f FROM Follow f WHERE f.follower.id = :followerId AND f.following.id = :followingId")
    Optional<Follow> existsByFollowerAndFollowing(@Param("followerId")Integer followerId, @Param("followingId") Integer followingId);
}