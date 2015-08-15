package finki.ask.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.Question;


public interface QuestionRepository extends JpaRepository<Question, Long>{
}
