package ru.hackaton.backend.models.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;


@Data
@Entity
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "email", updatable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "score")
    private Long score = 0L;

    @Column(name = "created_At", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<UserCourse> courses = new HashSet<>();

    // Constructor for MyUserDetails
    public User(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String email, String name, String avatar, LocalDateTime createdAt, LocalDateTime updatedAt, List<UserRole> roles) {
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
    }

    public void addCourse(Course course) {
        courses.add(new UserCourse(this, course));
    }

    public void removeCourse(Course course) {
        //TODO Is this correct? I'm not sure. This needs to be tested
        UserCourse userCourse = new UserCourse(this, course);
        courses.remove(userCourse);
    }

    public Set<UserCourse> getCourses() {
        return Collections.unmodifiableSet(courses);
    }
}