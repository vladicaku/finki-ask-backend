package finki.ask.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Result;

public interface ResultRepository extends JpaRepository<Result, Long>, ResultRepositoryCustom{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Result findOne(Long arg0);
	

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Result> findAll();
	

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	<S extends Result> S save(S arg0);
	
}
