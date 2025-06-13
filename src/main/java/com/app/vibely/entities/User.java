package com.app.vibely.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "bio", length = Integer.MAX_VALUE)
    private String bio;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "profile_picture", length = Integer.MAX_VALUE)
    private String profilePicture;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "user")
    private Set<Bookmark> bookmarks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<CommentLike> commentLikes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<EventComment> eventComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<EventLike> eventLikes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<EventSave> eventSaves = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "following")
    private Set<Follow> follows = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Like> likes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Post> posts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<SavedPost> savedPosts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Story> stories = new LinkedHashSet<>();

}