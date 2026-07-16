package sathish292004.questionservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sathish292004.questionservice.model.Question;


import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);


    @Query(value = "SELECT q.id FROM question q WHERE q.category = :category ORDER BY RANDOM() limit :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(String category, int numQ);
}
