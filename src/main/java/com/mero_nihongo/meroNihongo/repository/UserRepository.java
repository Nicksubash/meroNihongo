package com.mero_nihongo.meroNihongo.repository;

import com.mero_nihongo.meroNihongo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    User findByEmail(String email);
}
