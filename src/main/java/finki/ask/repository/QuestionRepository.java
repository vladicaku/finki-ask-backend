package finki.ask.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.Question;

@Transactional
public interface QuestionRepository extends JpaRepository<Question, Long>{
}
