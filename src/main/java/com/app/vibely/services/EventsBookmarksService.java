package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.EventsBookmarksDto;
import com.app.vibely.entities.Event;
import com.app.vibely.entities.EventBookmarks;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.EventsBookmarksMapper;
import com.app.vibely.repositories.EventBookmarksRepository;
import com.app.vibely.repositories.EventRepository;
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

@Service
@AllArgsConstructor
public class EventsBookmarksService {

    private final UserRepository userRepository;
    private final EventBookmarksRepository bookmarkRepository;
    private final EventsBookmarksMapper eventsBookmarksMapper;
    private final EventRepository eventRepository;

    // ✅ Add or remove bookmark (toggle)
    @Transactional
    public void toggleBookmark(Integer eventId, Integer userId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException( "User not authorized to perform this action"));

        EventBookmarks existingLike = bookmarkRepository.findByEventIdAndUserId(eventId, userId);
        if (existingLike != null) {
            bookmarkRepository.delete(existingLike);
        } else {
            EventBookmarks bookmark = new EventBookmarks();
            bookmark.setEvent(event);
            bookmark.setUser(user);
            bookmark.setBookmarkedAt(Instant.now());
            bookmarkRepository.save(bookmark);
        }
    }

    public PagedResponse<EventsBookmarksDto> getBookmarksByEventId(Integer eventId, int page, int size) {
        if (!eventRepository.existsById(eventId)) throw new ResourceNotFoundException( "Event not found");

        Pageable pageable = PageRequest.of(page, size);
        Page<EventBookmarks> bookmarkPage = bookmarkRepository.findByEventId(eventId, pageable);

        List<EventsBookmarksDto> bookmarksDto = bookmarkPage.stream().map(eventsBookmarksMapper::toDto).toList();
        return new PagedResponse<>(bookmarksDto, page, size, bookmarkPage.getTotalElements(), bookmarkPage.getTotalPages(), bookmarkPage.hasNext(), bookmarkPage.hasPrevious());

    }

    public boolean isEventSaved(Integer eventId , Integer userId) {
        EventBookmarks isSaved = bookmarkRepository.findByEventIdAndUserId(eventId , userId);
        return isSaved != null;
    }
}
