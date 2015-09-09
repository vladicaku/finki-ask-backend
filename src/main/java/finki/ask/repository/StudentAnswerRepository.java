package finki.ask.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.StudentAnswer;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long>, StudentAnswerRepositoryCustom{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<StudentAnswer> findAll();
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	StudentAnswer findOne(Long arg0);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	<S extends StudentAnswer> S save(S arg0);
	
}
