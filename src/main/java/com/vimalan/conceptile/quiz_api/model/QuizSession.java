package com.vimalan.conceptile.quiz_api.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<Question> answeredQuestions = new ArrayList<>();

    private int correctAnswers;
    private int incorrectAnswers;

    @ElementCollection
    private Set<Long> askedQuestionIds = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<Question> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public Set<Long> getAskedQuestionIds() {
        return askedQuestionIds;
    }

    public void setAskedQuestionIds(Set<Long> askedQuestionIds) {
        this.askedQuestionIds = askedQuestionIds;
    }
    
    public void addAnsweredQuestion(Question question) {
        answeredQuestions.add(question);
    }
    
    
    public boolean isQuestionAnswered(Long questionId) {
        return answeredQuestions.stream()
            .anyMatch(question -> question.getQuestionId().equals(questionId));
    }
    
    

}

