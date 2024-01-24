package com.example.gitrepoexplorer.domain.crud;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

interface BranchRepository extends Repository<Branch, Long> {

    @Query("select b from Branch b where b.id = :id")
    Optional<Branch> findById(Long id);
    @Modifying
    @Query("delete from Branch b where b.id = :id")
    void deleteById(Long id);
    @Modifying
    @Query("delete from Branch b where b.id in :ids")
    void deleteByIdIn(Collection<Long> ids);
    @Modifying
    @Query("delete from Branch b")
    void deleteAll();
    boolean existsById(Long id);

}