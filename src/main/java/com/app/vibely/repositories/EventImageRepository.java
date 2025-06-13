package com.app.vibely.repositories;

import com.app.vibely.entities.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventImageRepository extends JpaRepository<EventImage, Integer> {
}