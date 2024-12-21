package com.vimalan.conceptile.quiz_api.repository;

import com.vimalan.conceptile.quiz_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
