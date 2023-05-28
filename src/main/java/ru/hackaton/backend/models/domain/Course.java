package ru.hackaton.backend.models.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private CourseContent content;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "created_At", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;;

    @ManyToOne
    @JoinColumn(name = "art_id")
    private Art art;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<Lesson> lessons = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "study_program_id")
    private StudyProgram studyProgram;


    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setCourse(this);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
        lesson.setCourse(null);
    }

    public Set<Lesson> getLessons() {
        return Collections.unmodifiableSet(lessons);
    }


//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "school_art",
//            joinColumns = {@JoinColumn(name = "school_id")},
//            inverseJoinColumns = {@JoinColumn(name = "art_id")})
//    private Set<User> users = new HashSet<>();

}
