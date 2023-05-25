package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@Table(name = "art")
public class Art {

    @Id
    @SequenceGenerator(name = "art_sequence", sequenceName = "art_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "art_sequence")
    @Column(name = "id")
    private Long id;

    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "arts", fetch = FetchType.LAZY)
    private Set<School> schools;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "arts", fetch = FetchType.LAZY)
    private Set<Article> articles;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "art", fetch = FetchType.LAZY)
    private Set<Course> courses;

}
