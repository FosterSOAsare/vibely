package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.FollowDto;
import com.app.vibely.entities.Follow;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.FollowMapper;
import com.app.vibely.repositories.FollowRepository;
import com.app.vibely.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowMapper followMapper;
    // ✅ Add or remove like (toggle)
    @Transactional
    public void toggleFollow(Integer followingId, Integer userId )  {
        if(followingId.equals(userId)) throw new BadCredentialsException("User is not allowed to follow him/her self");
        User following = userRepository.findById(followingId).orElseThrow(() -> new ResourceNotFoundException("The  user id to be followed is invalid"));
        User user = userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException( "User not authorized to perform this action"));


        Optional<Follow> existingFollow = followRepository.existsByFollowerAndFollowing(user.getId() , following.getId());
        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get()); // Remove follow
        } else {
            Follow follow = new Follow();
            follow.setFollower(user);
            follow.setFollowing(following);
            follow.setCreatedAt(Instant.now());
            followRepository.save(follow);
        }
    }



    public PagedResponse<FollowDto> getFollowersByFollowingId(Integer followingId, int page, int size) {
        if (!userRepository.existsById(followingId)) throw new ResourceNotFoundException( "The user was not found");

        Pageable pageable = PageRequest.of(page, size);
        Page<Follow> followsPage = followRepository.findAllByFollowing_Id(followingId , pageable);

//        Map and return a pagedResponse
        List<FollowDto> followsDto = followsPage.stream().map((follow) -> {
            FollowDto followDto = followMapper.toDto(follow);
            followDto.setIsFollowing(followRepository.checkIfUserIsFollowed(follow.getId() , followingId));
            return followDto;
        }).toList();

        return new PagedResponse<>(followsDto, page, size, followsPage.getTotalElements(), followsPage.getTotalPages(), followsPage.hasNext(), followsPage.hasPrevious());
    }

    public PagedResponse<FollowDto> getMyFollowers(Integer followerId, int page, int size) {
        if (!userRepository.existsById(followerId)) throw new ResourceNotFoundException( "The user was not found");

        Pageable pageable = PageRequest.of(page, size);
        Page<Follow> followsPage = followRepository.findAllByFollower_Id(followerId , pageable);

//        Map and return a pagedResponse
        List<FollowDto> followsDto = followsPage.stream().map((follow) -> {
            FollowDto followDto = followMapper.toDto(follow);
            followDto.setIsFollowing(true);
            followDto.setUsername(follow.getFollowing().getUsername());
            followDto.setId(follow.getFollowing().getId());
            followDto.setProfilePicture(follow.getFollowing().getProfilePicture());
            return followDto;
        }).toList();

        return new PagedResponse<>(followsDto, page, size, followsPage.getTotalElements(), followsPage.getTotalPages(), followsPage.hasNext(), followsPage.hasPrevious());
    }
}
