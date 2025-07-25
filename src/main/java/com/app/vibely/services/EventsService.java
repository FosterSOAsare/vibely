package com.app.vibely.services;


import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.CreateEventRequest;
import com.app.vibely.dtos.EventsDto;
import com.app.vibely.entities.Event;
import com.app.vibely.entities.EventImage;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.EventsMapper;
import com.app.vibely.repositories.EventRepository;
import com.app.vibely.repositories.FollowRepository;
import com.app.vibely.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventsService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final FollowRepository followRepository;
    private final EventsMapper eventsMapper;


    // Get all posts with pagination, newest first
    public PagedResponse<EventsDto> getAllEvents(int page, int size , Integer userId ) {
        Pageable pageable = PageRequest.of(page, size , Sort.by("id").descending());
        Page<Event> eventsPage = eventRepository.findAllByOrderByCreatedAtDesc(pageable);

        List<EventsDto> eventsDtos = eventsPage.stream().map(event -> {
            EventsDto dto = eventsMapper.toDto(event);
            // Check if user liked or saved this event as well as if user is following event owner
            dto.setIsLiked(event.isLiked(userId));
            dto.setIsSaved(event.isSaved(userId));
            dto.setIsFollowing(followRepository.checkIfUserIsFollowed(event.getUser().getId() , userId));
            return dto;
        }).toList();

        return new PagedResponse<>(eventsDtos, page, size, eventsPage.getTotalElements(), eventsPage.getTotalPages(), eventsPage.hasNext(), eventsPage.hasPrevious());
    }

    @Transactional
    public EventsDto createEvent(CreateEventRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Create event
        Event event = new Event();
        event.setUser(user);
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setCoordinatesLat(request.getCoordinates().get(0));
        event.setCoordinatesLng(request.getCoordinates().get(1));
        event.setEventTime(request.getEventTime());
        event.setPrice(request.getPrice());
        event.setCreatedAt(Instant.now());

        // Map image URLs to EventImage entities and attach to event
        Set<EventImage> images = request.getImages().stream()
                .map(url -> {
                    EventImage img = new EventImage();
                    img.setImageUrl(url);
                    img.setEvent(event); // Set back-reference
                    return img;
                })
                .collect(Collectors.toSet());

        event.setEventImages(images);

        // Save event and cascade saves images
        Event savedEvent = eventRepository.save(event);

        // Return DTO
        return eventsMapper.toDto(savedEvent);
    }

    public void deleteEventById(Integer postId , Integer userId) {

        // Check if event exists
        Event event =  eventRepository.findById(postId).orElse(null);
        if(event == null){
            throw new ResourceNotFoundException("Event not found with ID: " + postId);
        }

        //  Check if user is the owner of the event
        if(!event.getUser().getId().equals(userId)){
            throw new BadCredentialsException("User not allowed to perform this action");
        }
        eventRepository.deleteById(postId);

    }

    // Get posts by userId with optional startId (load more pattern)
    public PagedResponse<EventsDto> getPostsByUser(Integer userId, Integer startId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size , Sort.by("id").descending());
        Page<Event> eventsPage;

        if (startId != null) {
            eventsPage = eventRepository.findByUserIdAndIdLessThanEqualOrderByCreatedAtDesc(userId, startId, pageable);
        } else {
            eventsPage = eventRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        }

        //  Map through here and check isLiked or isSaved
        List<EventsDto> eventsDtos = eventsPage.stream().map(post -> {
            EventsDto dto = eventsMapper.toDto(post);
            // Check if user liked or saved this post
            dto.setIsLiked(post.isLiked(userId));
            dto.setIsSaved(post.isSaved(userId));
            return dto;
        }).toList();

        return new PagedResponse<>(eventsDtos, page, size, eventsPage.getTotalElements(), eventsPage.getTotalPages(), eventsPage.hasNext(), eventsPage.hasPrevious());
    }

    public List<String> createEventImages(Set<EventImage> eventImages){
        return eventImages.stream().map(EventImage::getImageUrl).toList();
    }

    public List<Double> createCoordinates (Double coordinatesLat , Double coordinatesLng){
        return List.of(coordinatesLat, coordinatesLng);
    }

}
