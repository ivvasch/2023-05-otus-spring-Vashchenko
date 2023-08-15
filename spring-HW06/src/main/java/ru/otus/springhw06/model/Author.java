package ru.otus.springhw06.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_author")
public class Author {
    @Id
    @Column()
    private String id;
    @Column(name = "name")
    private String authorName;
}
