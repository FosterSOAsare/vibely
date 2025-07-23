package com.app.vibely.services;

import com.app.vibely.dtos.*;
import com.app.vibely.entities.Story;
import com.app.vibely.entities.StoryView;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.StoriesMapper;
import com.app.vibely.mappers.UserMapper;
import com.app.vibely.repositories.StoryRepository;
import com.app.vibely.repositories.StoryViewRepository;
import com.app.vibely.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoriesService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final StoryViewRepository storyViewRepository;
    private final UserMapper userMapper;
    private final StoriesMapper storiesMapper;

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

    public List<StoriesDto> getStoriesByUserId(Integer userId , Integer currentUserId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return storyRepository.findByUserIdOrderByCreatedAtAsc(userId).stream()
                .map((story) -> {
                    StoriesDto storyDto = storiesMapper.toDto(story);
                    storyDto.setViewed(story.hasViewedStory(currentUserId));
                    return storyDto;
                })
                .toList();
    }

    public List<UserWithStoryDto> getUsersWithStories(Integer viewerId) {
        User current = userRepository.findById(viewerId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return storyRepository.findUsersWithStories().stream()
                .map((user) -> {
                    UserWithStoryDto storyUser = new  UserWithStoryDto(user.getUsername(), user.getId(), user.getProfilePicture(), true);
                    Optional<Story> latestOpt = storyRepository.findMostRecentByUser(user.getId());

                    // Check if user has viewed all stories
                    boolean viewed = false;
                    if (latestOpt.isPresent()) {
                        Story story = latestOpt.get();
                        viewed = storyViewRepository.existsByStoryIdAndViewerId(story.getId(), viewerId);
                    }
                    storyUser.setAllViewed(viewed);
                    return storyUser;
                })
                .toList();
    }

    public void deleteStoryById(Integer id) {
        if (!storyRepository.existsById(id)) {
            throw new EntityNotFoundException("Story with ID " + id + " not found");
        }
        storyRepository.deleteById(id);
    }

    public void markStoryAsViewed(Integer storyId, Integer userId) {
        User viewer = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Story story = storyRepository.findById(storyId).orElseThrow(() -> new EntityNotFoundException("Story with ID " + storyId + " not found"));
        // Check if view already exists
        boolean alreadyViewed = storyViewRepository.existsByStoryAndViewer(story, viewer);
        if (alreadyViewed) {
            return; // No need to add again
        }

        // Create and save new view
        StoryView storyView = new StoryView();
        storyView.setStory(story);
        storyView.setViewer(viewer);
        storyView.setViewedAt(Instant.now());

        storyViewRepository.save(storyView);
    }

    public List<StoryViewerDto> getStoryViewers(Integer storyId) {

        Story story = storyRepository.findById(storyId).orElseThrow(() -> new EntityNotFoundException("Story with ID " + storyId + " not found"));

        return storyViewRepository.findViewersByStory(story).stream()
                .map((viewer) -> {
                    User user = viewer.getViewer();
                    return new StoryViewerDto(user.getId() , user.getUsername() ,user.getProfilePicture() , viewer.getViewedAt() );
                })
                .toList();
    }

}
