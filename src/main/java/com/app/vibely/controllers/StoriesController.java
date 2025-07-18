package com.app.vibely.controllers;

import com.app.vibely.dtos.CreateStoryDto;
import com.app.vibely.dtos.StoriesDto;
import com.app.vibely.dtos.UserDto;
import com.app.vibely.entities.Story;
import com.app.vibely.mappers.StoriesMapper;
import com.app.vibely.services.StoriesService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<List<StoriesDto>> getStoriesByUser(@PathVariable Integer userId) {
        List<Story> stories = storyService.getStoriesByUserId(userId);
        List<StoriesDto> response = stories.stream()
                .map(storyMapper::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users-with-stories")
    public ResponseEntity<List<UserDto>> getUsersWithStories() {
        List<UserDto> users = storyService.getUsersWithStories();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteStory/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Integer id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.ok().build();
    }
}
