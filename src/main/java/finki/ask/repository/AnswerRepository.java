package finki.ask.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
}
