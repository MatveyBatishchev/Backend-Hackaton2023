package ru.hackaton.backend.models.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_course_lesson")
public class UserCourseLesson {

    @EmbeddedId
    UserCourseLessonId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, insertable = false), @JoinColumn(name = "course_id", referencedColumnName = "course_id", updatable = false, insertable = false)})
    private UserCourse userCourse;

    @MapsId("lessonId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Lesson lesson;

    public UserCourseLesson(UserCourse userCourse, Lesson lesson) {
        this.userCourse = userCourse;
        this.lesson = lesson;
        this.id = new UserCourseLessonId(userCourse.getId().getUserId(), userCourse.getId().getCourseId(), lesson.getId());
    }
}
