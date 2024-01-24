package com.example.gitrepoexplorer.domain.crud;

import com.example.gitrepoexplorer.domain.crud.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Branch extends BaseEntity {

    @Id
    @GeneratedValue(generator = "branch_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "branch_id_gen", sequenceName = "branch_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastCommitSha;

    public Branch(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }
}
