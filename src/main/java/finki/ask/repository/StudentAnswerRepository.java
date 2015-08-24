package finki.ask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.StudentAnswer;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long>, StudentAnswerRepositoryCustom{

}
