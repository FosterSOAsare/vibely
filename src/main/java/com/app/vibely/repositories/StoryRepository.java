package com.app.vibely.repositories;

import com.app.vibely.entities.Story;
import com.app.vibely.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findByUserIdOrderByCreatedAtAsc(Integer userId);

    //    This will first get the max createdAt by each user with a story. Then with the timestamp, it will fetch users from the story table using the timeStamp for sorting
    @Query("""
    SELECT s.user FROM Story s
    WHERE s.createdAt IN ( SELECT MAX(s2.createdAt) FROM Story s2 GROUP BY s2.user.id )
    ORDER BY s.createdAt DESC""")
    List<User> findUsersWithStories();


    @Query("SELECT s FROM Story s WHERE s.user.id = :ownerId ORDER BY s.createdAt DESC LIMIT 1")
    Optional<Story> findMostRecentByUser(@Param("ownerId") Integer ownerId);

}