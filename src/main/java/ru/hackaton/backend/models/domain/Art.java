package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;

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

}
