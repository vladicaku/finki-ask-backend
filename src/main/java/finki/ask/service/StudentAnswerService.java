package finki.ask.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public interface StudentAnswerService {
	
	public StudentAnswer save(StudentAnswer studentAnswer);
	
	public List<StudentAnswer> findAll();
	
	public StudentAnswer findSpecific(TestInstance testInstance, Test test, Question question, Answer answer);
	
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test);
	
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test, Question question);
	
	public List<StudentAnswer> findAllSpecific(Test test);
	
	public List<StudentAnswer> findAllSpecific(Test test, Question question);
	
	public StudentAnswer findById(long id);
	
	public void delete(long id);
	
}
