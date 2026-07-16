package sathish292004.questionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sathish292004.questionservice.model.Question;
import sathish292004.questionservice.model.QuestionWrapper;
import sathish292004.questionservice.model.Response;
import sathish292004.questionservice.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("/updateQuestion")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int questionId) {
        return new ResponseEntity<>(questionService.deleteQuestion(questionId), HttpStatus.OK);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz(
            @RequestParam("categoryName") String categoryName,
            @RequestParam("numQuestions") Integer numQuestions) {

        System.out.println("\n======================================");
        System.out.println("ENDPOINT : /question/generate");
        System.out.println("PORT     : " + environment.getProperty("local.server.port"));
        System.out.println("CATEGORY : " + categoryName);
        System.out.println("COUNT    : " + numQuestions);
        System.out.println("======================================");

        return questionService.generateQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsByIds(@RequestBody List<Integer> questionIds) {

        System.out.println("\n======================================");
        System.out.println("ENDPOINT : /question/getQuestions");
        System.out.println("PORT     : " + environment.getProperty("local.server.port"));
        System.out.println("QUESTION IDS : " + questionIds);
        System.out.println("======================================");

        return questionService.getQuestionsFromByIds(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> response) {

        System.out.println("\n======================================");
        System.out.println("ENDPOINT : /question/getScore");
        System.out.println("PORT     : " + environment.getProperty("local.server.port"));
        System.out.println("======================================");

        return questionService.getScore(response);
    }
}