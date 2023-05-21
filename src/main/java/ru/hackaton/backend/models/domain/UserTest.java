package ru.hackaton.backend.models.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_test")
public class UserTest {

    @EmbeddedId
    private UserTestId id = new UserTestId();

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
        this.id = new UserTestId(user.getId(), test.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTest userTest = (UserTest) o;
        return Objects.equals(user, test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, test);
    }
}
