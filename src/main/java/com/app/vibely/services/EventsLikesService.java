package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventsLikesDto;
import com.app.vibely.entities.*;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.EventsLikesMapper;
import com.app.vibely.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class EventsLikesService {
    private final EventLikeRepository likeRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventsLikesMapper eventsLikesMapper;

    // ✅ Add or remove like (toggle)
    @Transactional
    public void toggleLike(Integer eventId, Integer userId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException( "User not authorized to perform this action"));

        EventLike existingLike = likeRepository.findByEventIdAndUserId(eventId, userId);
        if (existingLike != null) {
            likeRepository.delete(existingLike); // Remove like (unlike)
        } else {
            EventLike like = new EventLike();
            like.setEvent(event);
            like.setUser(user);
            like.setCreatedAt(Instant.now());
            likeRepository.save(like);
        }
    }

    public PagedResponse<EventsLikesDto> getLikesByEventId(Integer eventId, int page, int size) {
        if (!eventRepository.existsById(eventId)) throw new ResourceNotFoundException( "Event not found");

        Pageable pageable = PageRequest.of(page, size);
        Page<EventLike> likesPage = likeRepository.findByEventId(eventId, pageable);

//        Map and return a pagedResponse
        List<EventsLikesDto> likesDto = likesPage.stream().map(eventsLikesMapper::toDto).toList();

        return new PagedResponse<>(likesDto, page, size, likesPage.getTotalElements(), likesPage.getTotalPages(), likesPage.hasNext(), likesPage.hasPrevious());
    }

    public boolean isEventLiked(Integer eventId , Integer userId) {
        EventLike isLiked = likeRepository.findByEventIdAndUserId(eventId , userId);
        return isLiked != null;
    }

    public Integer calculateEventLikes(Integer eventId) {
        return likeRepository.countByEventId(eventId);
    }


}
