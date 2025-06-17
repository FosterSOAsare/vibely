package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.Like;
import com.app.vibely.mappers.UserLikedPostsMapper;
import com.app.vibely.repositories.FollowRepository;
import com.app.vibely.repositories.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserLikedPostsServices {
    private final LikeRepository likeRepository;
    private final UserLikedPostsMapper userLikedPostsMapper;
    private final PostLikesService likesService;
    private final FollowRepository followRepository;
    private final PostCommentsService commentsService;
    private final PostBookmarksService bookmarksService;


    public PagedResponse<PostDto> getLikedPostsByUserId(Integer currentUserId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size , Sort.by("createdAt").descending());
        Page<Like> likedPage = likeRepository.findByUserId(currentUserId, pageable);

        List<PostDto> likedDto = likedPage.stream().map((liked) -> {
            PostDto postDto =  userLikedPostsMapper.toDto(liked);
//          set isLiked, isSaved , isFollowing, comments , likes
            postDto.setIsLiked(true);
            postDto.setIsSaved(bookmarksService.isPostSaved(postDto.getId() , currentUserId));
            postDto.setLikes(likesService.calculatePostLikes(postDto.getId()));
            postDto.setComments(commentsService.calculatePostComments(postDto.getId()));
            postDto.setIsFollowing(followRepository.checkIfUserIsFollowed(liked.getPost().getUser().getId() , currentUserId));
            return postDto;
        }).toList();
        return new PagedResponse<>(likedDto, page, size, likedPage.getTotalElements(), likedPage.getTotalPages(), likedPage.hasNext(), likedPage.hasPrevious());

    }
}
