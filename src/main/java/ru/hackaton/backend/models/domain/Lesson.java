package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @SequenceGenerator(name = "lesson_sequence", sequenceName = "lesson_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "lesson_order")
    private Integer lessonOrder;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private LessonContent content;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "created_At", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

    @ManyToOne
//    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "intro")
    private Boolean intro;


}
