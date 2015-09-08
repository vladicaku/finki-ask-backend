package finki.ask.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Question;
import finki.ask.model.Result;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;

@Transactional(isolation=Isolation.REPEATABLE_READ)
public interface ResultRepositoryCustom {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<Result> findAllSpecific(Test test);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<Result> findAllSpecific(Test test, Question question);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<Result> findAllSpecific(TestInstance testInstance, Test test);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public Result findSpecific(TestInstance testInstance, Test test, Question question);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public double sumPoints(TestInstance testInstance, Test test);
}
