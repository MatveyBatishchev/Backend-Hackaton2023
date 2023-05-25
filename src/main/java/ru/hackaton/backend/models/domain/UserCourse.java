package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_course")
public class UserCourse {

    @EmbeddedId
    private UserCourseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private Course course;

    @OneToMany
//    @JoinColumns({
//            @JoinColumn(name = "userId", referencedColumnName = )
//    })
    private Set<Lesson> completedLessons = new HashSet<>();

}
