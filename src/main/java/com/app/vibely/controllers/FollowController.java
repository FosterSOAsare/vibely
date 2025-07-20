package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.FollowDto;
import com.app.vibely.repositories.FollowRepository;
import com.app.vibely.services.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@SuppressWarnings("unused")
public class FollowController {
    private final FollowService followService;
    private final FollowRepository followRepository;

    //    Toggle following
    // ✅ Toggle like (like or unlike a post)
    @PostMapping("/{followingId}/follows")
    public ResponseEntity<?> toggleFollows(@PathVariable Integer followingId, Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());

        followService.toggleFollow(followingId, userId);
        return ResponseEntity.ok(Map.of("message", "Follow toggled successfully"));
    }

    // ✅ Get paginated follows of a user
    @GetMapping("/{userId}/followers")
    public ResponseEntity<PagedResponse<FollowDto>> getUserFollowers(@PathVariable Integer userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        PagedResponse<FollowDto> follows = followService.getFollowers(userId, page, size);
        return ResponseEntity.ok(follows);
    }

    @GetMapping("/{userId}/followings")
    public ResponseEntity<PagedResponse<FollowDto>> getUserFollowings(@PathVariable Integer userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        PagedResponse<FollowDto> follows = followService.getFollowings(userId, page, size);
        return ResponseEntity.ok(follows);
    }
}
