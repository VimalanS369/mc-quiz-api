package com.vimalan.conceptile.quiz_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vimalan.conceptile.quiz_api.model.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q ORDER BY RAND() LIMIT 1")
    Question findRandomQuestion();
    
    @Query("SELECT q FROM Question q WHERE q.id NOT IN :askedIds")
    List<Question> findUnaskedQuestions(@Param("askedIds") List<Long> askedIds);


}
