package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.PostLikesDto;
import com.app.vibely.entities.Like;
import com.app.vibely.entities.Post;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.PostLikesMapper;
import com.app.vibely.repositories.LikeRepository;
import com.app.vibely.repositories.PostRepository;
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
public class PostLikesService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikesMapper postLikesMapper;

    // ✅ Add or remove like (toggle)
    @Transactional
    public void toggleLike(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException( "User not authorized to perform this action"));

        Like existingLike = likeRepository.findByPostIdAndUserId(postId, userId);
        if (existingLike != null) {
            likeRepository.delete(existingLike); // Remove like (unlike)
        } else {
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            like.setCreatedAt(Instant.now());
            likeRepository.save(like);
        }
    }

    public PagedResponse<PostLikesDto> getLikesByPostId(Integer postId, int page, int size) {
        if (!postRepository.existsById(postId)) throw new ResourceNotFoundException( "Post not found");

        Pageable pageable = PageRequest.of(page, size);
        Page<Like> likesPage = likeRepository.findByPostId(postId, pageable);

//        Map and return a pagedResponse
        List<PostLikesDto> likesDto = likesPage.stream().map(postLikesMapper::toDto).toList();

        return new PagedResponse<>(likesDto, page, size, likesPage.getTotalElements(), likesPage.getTotalPages(), likesPage.hasNext(), likesPage.hasPrevious());
    }
}
