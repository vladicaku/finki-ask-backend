package finki.ask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
}
