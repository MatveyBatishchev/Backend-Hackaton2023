package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "test")
public class Test {

    @Id
    @SequenceGenerator(name = "test_sequence", sequenceName = "test_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
//    @NotEmpty
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at")
//    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
//    @NotNull
    private LocalDateTime updatedAt;

    @Column(name = "score_per_question")
    private Integer scorePerQuestion;

    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(fetch = FetchType.EAGER)
    private Art art;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter(AccessLevel.NONE)
    private Set<Question> questions = new HashSet<>();


    public void addQuestion(Question question) {
        questions.add(question);
        question.setTest(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setTest(null);
    }

    public Set<Question> getQuestions() {
        return Collections.unmodifiableSet(questions);
    }
}
