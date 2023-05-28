package ru.hackaton.backend.models.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_content")
public class CourseContent {

    @Id
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Course course;


    @Type(JsonType.class)
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;


}
