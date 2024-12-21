package com.vimalan.conceptile.quiz_api.repository;

import com.vimalan.conceptile.quiz_api.model.Answer;
import com.vimalan.conceptile.quiz_api.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    int countByQuizSession(QuizSession quizSession);
    int countByQuizSessionAndIsCorrect(QuizSession quizSession, boolean isCorrect);
}
