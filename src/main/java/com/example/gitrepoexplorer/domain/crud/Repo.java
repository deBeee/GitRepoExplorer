package com.example.gitrepoexplorer.domain.crud;

import com.example.gitrepoexplorer.domain.crud.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Repo extends BaseEntity {

    @Id
    @GeneratedValue(generator = "repo_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "repo_id_gen", sequenceName = "repo_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "repo_id")
    private Set<Branch> branches = new HashSet<>();

    public Repo(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public Repo(String owner, String name, Set<Branch> branches) {
        this.owner = owner;
        this.name = name;
        this.branches = branches;
    }

    public Set<Branch> addBranch(Branch branch){
        this.branches.add(branch);
        return this.branches;
    }

    public boolean removeBranchById(Long id){
        return this.branches.removeIf(branch -> branch.getId().equals(id));
    }
}
