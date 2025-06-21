package com.app.vibely.services;

import com.app.vibely.dtos.CreateStoryDto;
import com.app.vibely.dtos.UserDto;
import com.app.vibely.entities.Story;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.UserMapper;
import com.app.vibely.repositories.StoryRepository;
import com.app.vibely.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoriesService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Story createStory(CreateStoryDto dto, Integer userId) {
        // ✅ Check if the user exists before creating the story
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // ✅ Build and save the story
        Story story = new Story();
        story.setImageUrl(dto.getImageUrl());
        story.setCaption(dto.getCaption());
        story.setUser(user);
        story.setCreatedAt(Instant.now());

        return storyRepository.save(story);
    }

    public List<Story> getStoriesByUserId(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return storyRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<UserDto> getUsersWithStories() {
        return storyRepository.findUsersWithStories().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public void deleteStoryById(Integer id) {
        if (!storyRepository.existsById(id)) {
            throw new EntityNotFoundException("Story with ID " + id + " not found");
        }
        storyRepository.deleteById(id);
    }
}
