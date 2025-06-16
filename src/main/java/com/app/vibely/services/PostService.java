package com.app.vibely.services;

import com.app.vibely.dtos.CreatePostRequest;
import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.Post;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.PostMapper;
import com.app.vibely.repositories.PostRepository;
import com.app.vibely.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    // Get all posts with pagination, newest first
    public List<PostDto> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return posts.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    // Get posts by userId with optional startId (load more pattern)
    public List<PostDto> getPostsByUser(Integer userId, Optional<Integer> startId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Post> posts;

        if (startId.isPresent()) {
            posts = postRepository.findByUserIdAndIdLessThanOrderByCreatedAtDesc(userId, startId.get(), pageable);
        } else {
            posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        }

        return posts.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    // Delete a post and its likes, comments, bookmarks via cascade
    public void deletePostById(Integer postId , Integer userId) {

        // Check if post exists
        Post post =  postRepository.findById(postId).orElse(null);
        if(post == null){
            throw new ResourceNotFoundException("Post not found with ID: " + postId);
        }

        //  Check if user is the owner of the post
        if(!post.getUser().getId().equals(userId)){
            throw new BadCredentialsException("User not allowed to perform this action");
        }
        postRepository.deleteById(postId);

    }

    public PostDto createPost(CreatePostRequest request, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = new Post();
        post.setImageUrl(request.getImageUrl());
        post.setUser(user);
        post.setCaption(request.getCaption());
        post.setCreatedAt(Instant.now());
        postRepository.save(post);
        
        Post savedPost = postRepository.save(post);
        return postMapper.toDto(savedPost);
    }
}
