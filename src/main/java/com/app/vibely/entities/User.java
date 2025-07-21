package com.app.vibely.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "verification_code")
    private String verificationCode;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "user")
    private Set<Bookmark> bookmarks = new LinkedHashSet<>() ;

//    Users this user is following (aka "followings").
    @OneToMany(mappedBy = "following")
    private Set<Follow> followers = new LinkedHashSet<>();

//    Users who follow this user
    @OneToMany(mappedBy = "follower")
    private Set<Follow> followings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Story> stories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StoryView> storyViews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Like> likes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Post> posts = new LinkedHashSet<>();

    public int calculateFollowers(){
        return this.followers.size();
    }

    public int calculateFollowings(){
        return this.followings.size();
    }

    public boolean hasViewedAllStoriesOf(User otherUser) {
        // Get all story IDs of the other user
        Set<Integer> otherUserStoryIds = otherUser.getStories().stream()
                .map(Story::getId)
                .collect(Collectors.toSet());

        // Get all story IDs viewed by "this" user that belong to the other user
        Set<Integer> viewedStoryIds = this.storyViews.stream()
                .map(StoryView::getStory)
                .filter(story -> story.getUser().equals(otherUser))
                .map(Story::getId)
                .collect(Collectors.toSet());

        // Return true if every story ID from the other user is in the viewed list
        return viewedStoryIds.containsAll(otherUserStoryIds);
    }


}