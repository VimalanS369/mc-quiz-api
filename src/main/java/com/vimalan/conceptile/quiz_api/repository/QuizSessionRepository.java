package com.vimalan.conceptile.quiz_api.repository;

import com.vimalan.conceptile.quiz_api.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
}