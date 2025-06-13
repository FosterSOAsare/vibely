package com.app.vibely.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "coordinates_lat")
    private Double coordinatesLat;

    @Column(name = "coordinates_lng")
    private Double coordinatesLng;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "event")
    private Set<EventComment> eventComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event")
    private Set<EventImage> eventImages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event")
    private Set<EventLike> eventLikes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event")
    private Set<EventSave> eventSaves = new LinkedHashSet<>();

}