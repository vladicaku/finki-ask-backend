package finki.ask.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;

import finki.ask.model.Answer;

@Transactional
public interface AnswerRepository extends JpaRepository<Answer, Long>{
}
