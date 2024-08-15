package com.example.gitrepoexplorer.domain.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findFirstByEmail(String email);

    User save(User user);

    boolean existsByEmail(String email);
}
