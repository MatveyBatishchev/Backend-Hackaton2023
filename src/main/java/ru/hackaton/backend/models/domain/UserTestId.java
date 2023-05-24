package ru.hackaton.backend.models.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserTestId implements Serializable {


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "test_id")
    private Long testId;

}
