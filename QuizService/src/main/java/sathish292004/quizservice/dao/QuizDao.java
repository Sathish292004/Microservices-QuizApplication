package sathish292004.quizservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sathish292004.quizservice.model.Quiz;
public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
