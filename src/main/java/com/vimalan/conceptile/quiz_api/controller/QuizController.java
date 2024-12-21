package com.vimalan.conceptile.quiz_api.controller;

import com.vimalan.conceptile.quiz_api.model.Question;
import com.vimalan.conceptile.quiz_api.model.QuizSession;
import com.vimalan.conceptile.quiz_api.model.User;
import com.vimalan.conceptile.quiz_api.repository.QuestionRepository;
import com.vimalan.conceptile.quiz_api.repository.QuizSessionRepository;
import com.vimalan.conceptile.quiz_api.repository.UserRepository;
import com.vimalan.conceptile.quiz_api.dto.AnswerSubmission;
import com.vimalan.conceptile.quiz_api.service.QuizService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Random;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizSessionRepository quizSessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private QuizSession currentQuizSession;

    @GetMapping("/start")
    public String startQuiz() {
        // Assume a single user for simplicity
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create a new quiz session
        currentQuizSession = new QuizSession();
        currentQuizSession.setUser(user);
        currentQuizSession = quizSessionRepository.save(currentQuizSession);

        return "Quiz session started!";
    }

    @GetMapping("/question")
    @Transactional
    public Question getRandomQuestion() {
        if (currentQuizSession == null) {
            throw new IllegalStateException("Quiz session has not started.");
        }

        // Get the list of questions that have already been asked
        List<Long> askedQuestionIds = new ArrayList<>(currentQuizSession.getAskedQuestionIds());


        // Retrieve a random unasked question
        Question question = getRandomUnaskedQuestion(askedQuestionIds);

        if (question == null) {
            throw new IllegalStateException("No more questions available.");
        }

        // Mark this question as asked
        currentQuizSession.getAskedQuestionIds().add(question.getQuestionId());

        quizSessionRepository.save(currentQuizSession);

        return question;
    }

    private Question getRandomUnaskedQuestion(List<Long> askedQuestionIds) {
        List<Question> unaskedQuestions = questionRepository.findAll().stream()
                .filter(q -> !askedQuestionIds.contains(q.getQuestionId()))
                .collect(Collectors.toList());

        if (unaskedQuestions.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return unaskedQuestions.get(random.nextInt(unaskedQuestions.size()));
    }


    @PostMapping("/answer")
    public String submitAnswer(@RequestBody AnswerSubmission submission) {
        if (currentQuizSession == null) {
            return "Please start a quiz session first.";
        }

        Long questionId = submission.getQuestionId();
        String selectedOption = submission.getSelectedOption();
        
        // Check if the question has already been answered
        if (currentQuizSession.isQuestionAnswered(questionId)) {
            return "This question has already been answered.";
        }
        
        // Logic to check if the answer is correct
        boolean isCorrect = quizService.validateAnswer(questionId, selectedOption);

        // Record the answer
        quizService.recordAnswer(currentQuizSession, questionId, isCorrect);

        return isCorrect ? "Correct Answer!" : "Incorrect Answer.";
    }

    @GetMapping("/summary")
    public String getSummary() {
        if (currentQuizSession == null) {
            return "Please start a quiz session first.";
        }

        int totalAnswered = quizService.getTotalAnswered(currentQuizSession);
        int correctAnswers = quizService.getCorrectAnswers(currentQuizSession);
        int incorrectAnswers = totalAnswered - correctAnswers;

        return String.format("Total answered: %d, Correct: %d, Incorrect: %d", totalAnswered, correctAnswers, incorrectAnswers);
    }
}
