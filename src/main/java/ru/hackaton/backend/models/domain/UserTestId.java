package ru.hackaton.backend.models.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Embeddable
public class UserTestId implements Serializable {


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "test_id")
    private Long testId;

}
