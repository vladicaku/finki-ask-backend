package finki.ask.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.StudentAnswer;

@Transactional(isolation=Isolation.READ_COMMITTED)
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long>, StudentAnswerRepositoryCustom{

	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<StudentAnswer> findAll();
	
	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	StudentAnswer findOne(Long arg0);
	
	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	<S extends StudentAnswer> S save(S arg0);
}
