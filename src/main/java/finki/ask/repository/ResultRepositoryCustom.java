package finki.ask.repository;

import java.util.List;

import finki.ask.model.Question;
import finki.ask.model.Result;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;

public interface ResultRepositoryCustom {
	public List<Result> findAllSpecific(Test test);
	public List<Result> findAllSpecific(Test test, Question question);
	public List<Result> findAllSpecific(TestInstance testInstance, Test test);
	public Result findSpecific(TestInstance testInstance, Test test, Question question);
	public double sumPoints(TestInstance testInstance, Test test);
}
