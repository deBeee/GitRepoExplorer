package com.example.gitrepoexplorer.infrastructure.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "repo")
public class Repo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    String owner;

    @Column(nullable = false)
    String name;

    public Repo(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }
}
