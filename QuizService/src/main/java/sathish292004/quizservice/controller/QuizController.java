package sathish292004.quizservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sathish292004.quizservice.model.QuestionWrapper;
import sathish292004.quizservice.model.Response;
import sathish292004.quizservice.service.QuizService;


import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizdto) {
        return quizService.createQuiz(quizdto.getCategoryName(), quizdto.getNumQuestion(), quizdto.getTitle());
    }

    @PostMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses) {

        return quizService.calculateResult(id, responses);

    }
}


