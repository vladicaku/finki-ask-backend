package finki.ask.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;

public interface StudentAnswerRepositoryCustom {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public StudentAnswer findSpecific(TestInstance testInstance, Test test, Question question, Answer answer);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test, Question question);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<StudentAnswer> findAllSpecific(Test test);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<StudentAnswer> findAllSpecific(Test test, Question question);
	
}
