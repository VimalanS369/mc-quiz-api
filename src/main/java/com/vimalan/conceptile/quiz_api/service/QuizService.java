package com.vimalan.conceptile.quiz_api.service;

import com.vimalan.conceptile.quiz_api.model.Answer;
import com.vimalan.conceptile.quiz_api.model.Question;
import com.vimalan.conceptile.quiz_api.model.QuizSession;
import com.vimalan.conceptile.quiz_api.repository.QuestionRepository;
import com.vimalan.conceptile.quiz_api.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    // Get a random question
    public Question getRandomQuestion() {
        List<Question> questions = questionRepository.findAll(); // Fetch all questions from the DB
        Random rand = new Random();
        Question randomQuestion = questions.get(rand.nextInt(questions.size())); // Pick a random question
        return randomQuestion;
    }

    // Validate the selected answer
    public boolean validateAnswer(Long questionId, String selectedOption) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
        return question.getCorrectAnswer().equals(selectedOption);
    }

    public void recordAnswer(QuizSession quizSession, Long questionId, boolean isCorrect) {
        // Check if the question has already been answered in the current session
        if (quizSession.isQuestionAnswered(questionId)) {
            throw new IllegalArgumentException("This question has already been answered.");
        }

        // Find the question by its ID
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        // Create a new Answer object and set its values
        Answer answer = new Answer();
        answer.setQuizSession(quizSession);  // Associate with the quiz session
        answer.setQuestion(question);       // Set the question for this answer
        answer.setCorrect(isCorrect);       // Mark if the answer is correct

        // Save the answer to the database
        answerRepository.save(answer);

        // Mark the question as answered in the session
        quizSession.addAnsweredQuestion(question);  // Add Question object to the list
    }

    public int getTotalAnswered(QuizSession quizSession) {
        // Get the count of all answers related to the current quiz session
        return answerRepository.countByQuizSession(quizSession);
    }

    /**
     * Get the number of correct answers submitted for the quiz session
     */
    public int getCorrectAnswers(QuizSession quizSession) {
        // Get the count of correct answers related to the current quiz session
        return answerRepository.countByQuizSessionAndIsCorrect(quizSession, true);
    }
}
