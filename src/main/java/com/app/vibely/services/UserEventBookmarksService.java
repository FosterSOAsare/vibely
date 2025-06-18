package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventsDto;
import com.app.vibely.entities.EventBookmarks;
import com.app.vibely.mappers.UserEventBookmarksMapper;
import com.app.vibely.repositories.EventBookmarksRepository;
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
public class UserEventBookmarksService {
    private final EventBookmarksRepository bookmarksRepository;
    private final UserEventBookmarksMapper userEventBookmarksMapper;
    private final EventsLikesService likesService;
    private final FollowRepository followRepository;
    private final EventsCommentsService commentsService;
    private final EventsService eventsService;


    public PagedResponse<EventsDto> getBookmarksByUserId(Integer currentUserId, int page, int size) {


        Pageable pageable = PageRequest.of(page, size , Sort.by("bookmarkedAt").descending());
        Page<EventBookmarks> bookmarkPage = bookmarksRepository.findByUserId(currentUserId, pageable);

        List<EventsDto> bookmarksDto = bookmarkPage.stream().map((bookmark) -> {
            EventsDto eventDto =  userEventBookmarksMapper.toDto(bookmark);

//          set isLiked, isSaved , isFollowing, comments , likes , coordinates and images
            eventDto.setIsLiked(likesService.isEventLiked(eventDto.getId(), currentUserId));
            eventDto.setIsSaved(true);
            eventDto.setLikes(likesService.calculateEventLikes(eventDto.getId()));
            eventDto.setComments(commentsService.calculateEventComments(eventDto.getId()));
            eventDto.setIsFollowing(followRepository.checkIfUserIsFollowed(bookmark.getEvent().getUser().getId() , currentUserId));
            eventDto.setImages(eventsService.createEventImages(bookmark.getEvent().getEventImages()));
            eventDto.setCoordinates(eventsService.createCoordinates(bookmark.getEvent().getCoordinatesLat(), bookmark.getEvent().getCoordinatesLng()));
            return eventDto;
        }).toList();
        return new PagedResponse<>(bookmarksDto, page, size, bookmarkPage.getTotalElements(), bookmarkPage.getTotalPages(), bookmarkPage.hasNext(), bookmarkPage.hasPrevious());

    }
}
