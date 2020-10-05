package ru.testsa.my_test.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "university_group")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "group_name")
    String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.MERGE)
    Set<Student> studentSet;

    @Column(name = "creation_date")
    LocalDateTime creationDate;

}
