package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventsDto;
import com.app.vibely.entities.EventLike;
import com.app.vibely.mappers.UserLikedEventsMapper;
import com.app.vibely.repositories.EventLikeRepository;
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
public class UserLikedEventsService {
    private final EventLikeRepository likeRepository;
    private final UserLikedEventsMapper userLikedEventsMapper;
    private final EventsLikesService likesService;
    private final EventsBookmarksService bookmarksService;
    private final FollowRepository followRepository;
    private final PostCommentsService commentsService;
    private final EventsService eventsService;


    public PagedResponse<EventsDto> getLikedEventsByUserId(Integer currentUserId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size , Sort.by("createdAt").descending());
        Page<EventLike> likePage = likeRepository.findByUserId(currentUserId, pageable);

        List<EventsDto> bookmarksDto = likePage.stream().map((like) -> {
            EventsDto eventDto =  userLikedEventsMapper.toDto(like);

//            set isLiked, isSaved , isFollowing, comments , likes , coordinates and images
            eventDto.setIsLiked(true);
            eventDto.setIsSaved(bookmarksService.isEventSaved(eventDto.getId() , currentUserId));
            eventDto.setLikes(likesService.calculateEventLikes(eventDto.getId()));
            eventDto.setComments(commentsService.calculatePostComments(eventDto.getId()));
            eventDto.setIsFollowing(followRepository.checkIfUserIsFollowed(like.getEvent().getUser().getId() , currentUserId));
            eventDto.setImages(eventsService.createEventImages(like.getEvent().getEventImages()));
            eventDto.setCoordinates(eventsService.createCoordinates(like.getEvent().getCoordinatesLat(), like.getEvent().getCoordinatesLng()));
            return eventDto;
        }).toList();
        return new PagedResponse<>(bookmarksDto, page, size, likePage.getTotalElements(), likePage.getTotalPages(), likePage.hasNext(), likePage.hasPrevious());

    }
}
