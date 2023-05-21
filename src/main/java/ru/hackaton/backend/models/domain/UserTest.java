package ru.hackaton.backend.models.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_test")
public class UserTest {

    @EmbeddedId
    private UserTestId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("testId")
    private Test test;

    @Column(name = "score")
    private Integer score;

    @Column(name = "passed_at")
    private LocalDateTime passedAt;

    public UserTest(User user, Test test) {
        this.user = user;
        this.test = test;
    }
}
