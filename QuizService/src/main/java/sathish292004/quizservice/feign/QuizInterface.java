package sathish292004.quizservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sathish292004.quizservice.model.QuestionWrapper;
import sathish292004.quizservice.model.Response;

import java.util.List;

@FeignClient(name = "QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("/question/generate")
    ResponseEntity<List<Integer>> getQuestionForQuiz(
            @RequestParam("categoryName") String categoryName,
            @RequestParam("numQuestions") Integer numQuestions
    );

    @PostMapping("/question/getQuestions")
    ResponseEntity<List<QuestionWrapper>> getQuestionsByIds(
            @RequestBody List<Integer> questionIds
    );

    @PostMapping("/question/getScore")
    ResponseEntity<Integer> getScore(
            @RequestBody List<Response> responses
    );
}