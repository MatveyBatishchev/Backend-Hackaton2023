package ru.hackaton.backend.models.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "school_content")
public class SchoolContent {

    @Id
    private long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "school_id")
    private School school;

    @Type(JsonType.class)
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;

}
