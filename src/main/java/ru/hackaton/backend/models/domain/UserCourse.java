package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_course")
public class UserCourse {

    @EmbeddedId
    private UserCourseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course;

    @Transient
    private Integer completion;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userCourse")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserCourseLesson> completedLessons = new HashSet<>();


    public UserCourse(User user, Course course) {
        this.user = user;
        this.course = course;
        this.id = new UserCourseId(user.getId(), course.getId());
    }

}
