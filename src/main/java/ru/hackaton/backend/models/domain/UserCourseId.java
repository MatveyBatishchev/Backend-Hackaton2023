package ru.hackaton.backend.models.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserCourseId implements Serializable {

    @Column(name = "user_id", updatable = false, insertable = false)
//    @Column(name = "user_id")
    private Long userId;

    @Column(name = "course_id", updatable = false, insertable = false)
//    @Column(name = "course_id")
    private Long courseId;


}
