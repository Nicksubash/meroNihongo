package com.mero_nihongo.meroNihongo.repository;

import com.mero_nihongo.meroNihongo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
}
