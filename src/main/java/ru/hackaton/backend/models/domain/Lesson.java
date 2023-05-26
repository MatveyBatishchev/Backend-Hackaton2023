package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @SequenceGenerator(name = "lesson_sequence", sequenceName = "lesson_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "order")
    private Integer order;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private LessonContent content;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    private Course course;

    @Column(name = "intro")
    private Boolean intro;


}