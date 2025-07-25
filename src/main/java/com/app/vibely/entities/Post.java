package com.app.vibely.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "posts")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "caption", length = Integer.MAX_VALUE)
    private String caption;

    @Column(name = "image_url", length = Integer.MAX_VALUE)
    private String imageUrl;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Bookmark> bookmarks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "post" , cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "post" , cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Like> likes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "post" , cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<SavedPost> savedPosts = new LinkedHashSet<>();


    public int calculateComments() {
        return comments != null ? comments.size() : 0;
    }

    public int calculateLikes() {
        return likes != null ? likes.size() : 0;
    }

    public boolean isLiked(Integer userId) {
        return this.likes.stream().anyMatch(like -> like.getUser().getId().equals(userId));
    }

    public boolean isSaved(Integer userId) {
        return this.bookmarks.stream().anyMatch(bookmark -> bookmark.getUser().getId().equals(userId));
    }


}