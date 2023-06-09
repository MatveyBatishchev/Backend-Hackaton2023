package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "question")
public class Question {

    @Id
    @SequenceGenerator(name = "question_sequence", sequenceName = "question_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "video")
    private String video;

    @Column(name = "audio")
    private String audio;

    @Column(name = "image")
    private String image;

    @Column(name = "explanation")
    private String explanation;

    @ManyToOne
//    @NotNull
    private Test test;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter(AccessLevel.NONE)
    private Set<Answer> answers = new HashSet<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }

    public Set<Answer> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }
}