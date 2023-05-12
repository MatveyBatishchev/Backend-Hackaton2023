package ru.hackaton.backend.models.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "news_content")
public class NewsContent {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "news_id")
    private News news;

    @Type(JsonType.class)
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;

}
