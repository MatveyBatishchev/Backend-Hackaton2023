package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "study_program")
public class StudyProgram {

    @Id
    @SequenceGenerator(name = "study_program_seq", sequenceName = "study_program_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "study_program_seq")
    @Column(name = "id")
    private long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "studyPrograms")
    private Set<School> schools = new HashSet<>();

    @Column(name = "name")
    private String name;

}
