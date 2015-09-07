package finki.ask.repository;

import java.util.List;

import javax.transaction.Transactional;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;

@Transactional
public interface StudentAnswerRepositoryCustom {
	public StudentAnswer findSpecific(TestInstance testInstance, Test test, Question question, Answer answer);
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test);
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test, Question question);
	public List<StudentAnswer> findAllSpecific(Test test);
	public List<StudentAnswer> findAllSpecific(Test test, Question question);
}
