package com.app.vibely.controllers;

import com.app.vibely.dtos.*;
import com.app.vibely.entities.Story;
import com.app.vibely.mappers.StoriesMapper;
import com.app.vibely.services.StoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
@SuppressWarnings("unused")
public class StoriesController {

    private final StoriesService storyService;
    private final StoriesMapper storyMapper;


    @PostMapping
    public ResponseEntity<StoriesDto> createStory(@Valid @RequestBody CreateStoryDto createStoryDto , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        Story story = storyService.createStory(createStoryDto, userId);
        StoriesDto response = storyMapper.toDto(story);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StoriesDto>> getStoriesByUser(@PathVariable Integer userId , Principal principal) {
        Integer currentUserId = Integer.parseInt(principal.getName());
        List<StoriesDto> stories = storyService.getStoriesByUserId(userId , currentUserId);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/users-with-stories")
    public ResponseEntity<List<UserWithStoryDto>> getUsersWithStories(Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        List<UserWithStoryDto> users = storyService.getUsersWithStories(userId);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteStory/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Integer id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mark-as-viewed/{id}")
    public ResponseEntity<Void> markStoryAsViewed(@PathVariable Integer id , Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        storyService.markStoryAsViewed(id , userId );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/viewers/{id}")
    public ResponseEntity<List<StoryViewerDto>> getStoryViewers(@PathVariable Integer id) {
        List<StoryViewerDto> users = storyService.getStoryViewers(id );
        return ResponseEntity.ok(users);
    }


}
