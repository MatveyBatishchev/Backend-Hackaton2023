package ru.hackaton.backend.models.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article_content")
public class ArticleContent {

    @Id
    private long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "article_id")
    private Article article;

    @Type(JsonType.class)
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;

}