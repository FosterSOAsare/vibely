package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.PostBookmarksDto;
import com.app.vibely.entities.Bookmark;
import com.app.vibely.entities.Post;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.PostBookmarksMapper;
import com.app.vibely.repositories.BookmarkRepository;
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
public class PostBookmarksService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PostBookmarksMapper postBookmarksMapper;

    // ✅ Add or remove like (toggle)
    @Transactional
    public void toggleBookmark(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException( "User not authorized to perform this action"));

        Bookmark existingLike = bookmarkRepository.findByPostIdAndUserId(postId, userId);
        if (existingLike != null) {
            bookmarkRepository.delete(existingLike);
        } else {
            Bookmark bookmark = new Bookmark();
            bookmark.setPost(post);
            bookmark.setUser(user);
            bookmark.setBookmarkedAt(Instant.now());
            bookmarkRepository.save(bookmark);
        }
    }

    public PagedResponse<PostBookmarksDto> getBookmarksByPostId(Integer postId, int page, int size) {
        if (!postRepository.existsById(postId)) throw new ResourceNotFoundException( "Post not found");

        Pageable pageable = PageRequest.of(page, size);
        Page<Bookmark> bookmarkPage = bookmarkRepository.findByPostId(postId, pageable);

        List<PostBookmarksDto> bookmarksDto = bookmarkPage.stream().map(postBookmarksMapper::toDto).toList();
        return new PagedResponse<>(bookmarksDto, page, size, bookmarkPage.getTotalElements(), bookmarkPage.getTotalPages(), bookmarkPage.hasNext(), bookmarkPage.hasPrevious());

    }
}
