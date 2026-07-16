package sathish292004.quizservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sathish292004.quizservice.dao.QuizDao;
import sathish292004.quizservice.feign.QuizInterface;
import sathish292004.quizservice.model.QuestionWrapper;
import sathish292004.quizservice.model.Quiz;
import sathish292004.quizservice.model.Response;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, Integer numQuestion, String title) {

        List<Integer> questions = quizInterface
                .getQuestionForQuiz(category, numQuestion)
                .getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Optional<Quiz> quiz = quizDao.findById(id);

        if (quiz.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Integer> questionIds = quiz.get().getQuestions();

        // Only ONE Feign call
        return quizInterface.getQuestionsByIds(questionIds);
    }

    public ResponseEntity<String> calculateResult(int id, List<Response> responses) {

        ResponseEntity<Integer> score = quizInterface.getScore(responses);

        return new ResponseEntity<>(
                "Your score is: " + score.getBody(),
                HttpStatus.OK
        );
    }
}