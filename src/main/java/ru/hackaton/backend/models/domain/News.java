package ru.hackaton.backend.models.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "news")
public class News {

    @Id
    @SequenceGenerator(name = "news_sequence", sequenceName = "news_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_sequence")
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "published")
    private Boolean published;

    @Type(JsonType.class)
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;

    @Column(name = "created_At", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "news_category",
            joinColumns = {@JoinColumn(name = "news_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories = new HashSet<>();

}
