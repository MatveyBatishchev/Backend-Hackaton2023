package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @SequenceGenerator(name = "answer_sequence", sequenceName = "answer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "video")
    private String video;

    @Column(name = "audio")
    private String audio;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "is_correct")
//    @NotNull
    private Boolean isCorrect;

    @ManyToOne
//    @NotNull
    private Question question;

}