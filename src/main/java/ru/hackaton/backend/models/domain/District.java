package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "district")
public class District {

    @Id
    @SequenceGenerator(name = "district_seq", sequenceName = "district_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_seq")
    @Column(name = "id")
    private long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "district")
    private Set<School> schools = new HashSet<>();

    @Column(name = "name")
    private String name;

}
