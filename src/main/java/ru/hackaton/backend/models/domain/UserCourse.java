package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private Course course;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_course_lesson",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                    @JoinColumn(name = "course_id", referencedColumnName = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "lesson_id")})
    private Set<Lesson> completedLessons = new HashSet<>();


    public UserCourse(User user, Course course) {
        this.user = user;
        this.course = course;
        this.id = new UserCourseId(user.getId(), course.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourse that = (UserCourse) o;
        return Objects.equals(user, that.user) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, course);
    }
}
