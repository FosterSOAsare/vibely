package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.Bookmark;
import com.app.vibely.mappers.UserPostBookmarksMapper;
import com.app.vibely.repositories.BookmarkRepository;
import com.app.vibely.repositories.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserPostBookmarksService {
    private final BookmarkRepository bookmarkRepository;
    private final UserPostBookmarksMapper userPostBookmarksMapper;
    private final PostLikesService likesService;
    private final FollowRepository followRepository;
    private final PostCommentsService commentsService;


    public PagedResponse<PostDto> getBookmarksByUserId(Integer currentUserId, int page, int size) {


        Pageable pageable = PageRequest.of(page, size , Sort.by("bookmarkedAt").descending());
        Page<Bookmark> bookmarkPage = bookmarkRepository.findByUserId(currentUserId, pageable);

        List<PostDto> bookmarksDto = bookmarkPage.stream().map((bookmark) -> {
            PostDto postDto =  userPostBookmarksMapper.toDto(bookmark);

//          set isLiked, isSaved , isFollowing, comments , likes
            postDto.setIsLiked(likesService.isPostLiked(bookmark.getPost().getId(), currentUserId));
            postDto.setIsSaved(true);
            postDto.setLikes(likesService.calculatePostLikes(bookmark.getPost().getId()));
            postDto.setComments(commentsService.calculatePostComments(bookmark.getPost().getId()));
            postDto.setIsFollowing(followRepository.checkIfUserIsFollowed(bookmark.getPost().getUser().getId() , currentUserId));
            return postDto;
        }).toList();
        return new PagedResponse<>(bookmarksDto, page, size, bookmarkPage.getTotalElements(), bookmarkPage.getTotalPages(), bookmarkPage.hasNext(), bookmarkPage.hasPrevious());

    }
}
