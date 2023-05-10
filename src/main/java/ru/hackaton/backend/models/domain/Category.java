package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @Column(name = "id")
    private long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private Set<News> news = new HashSet<>();

    @Column(name = "name")
    private String name;

}
