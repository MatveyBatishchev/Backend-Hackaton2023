package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "achievement")
public class Achievement {

    @Id
    @SequenceGenerator(name = "achievement_sequence", sequenceName = "achievement_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "achievement_sequence")
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    private boolean received;

    @Column(name = "success_info")
    private String successInfo;

    @Column(name = "painting_name")
    private String paintingName;

    @Column(name = "painting_caption")
    private String paintingCaption;

    @Column(name = "painting_description")
    private String paintingDescription;

    @Column(name = "achievement_type")
    @Enumerated(EnumType.ORDINAL)
    AchievementType achievementType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_achievement",
            joinColumns = {@JoinColumn(name = "achievement_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    Set<User> user = new HashSet<>();

}
