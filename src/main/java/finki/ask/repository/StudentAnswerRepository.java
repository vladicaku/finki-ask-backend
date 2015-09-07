package finki.ask.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.StudentAnswer;

@Transactional
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long>, StudentAnswerRepositoryCustom{

}
