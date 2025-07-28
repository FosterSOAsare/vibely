package com.app.vibely.repositories;

import com.app.vibely.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);




    @Query("SELECT u FROM User u WHERE u.username = :credential OR u.email = :credential")
    Optional<User> findByUsernameOrEmail(@Param("credential") String credential);

    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<User> findByUsernameOrNameContainingIgnoreCase(@Param("search") String search, Pageable pageable);
}
