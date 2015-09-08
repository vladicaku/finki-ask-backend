package finki.ask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Answer;

@Transactional()
public interface AnswerRepository extends JpaRepository<Answer, Long>{
}
