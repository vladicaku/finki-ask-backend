package finki.ask.service;

import java.util.List;

import javax.transaction.Transactional;

import finki.ask.model.Question;
import finki.ask.model.Result;
import finki.ask.model.ResultInfo;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;

@Transactional
public interface ResultService {
	
	public Result save(Result result);
	
	public List<Result> findAll();
	
	public List<Result> findAllSpecific(TestInstance testInstance, Test test);
	
	public Result findSpecific(TestInstance testInstance, Test test, Question question);
	
	public List<Result> findAllSpecific(Test test);
	
	public List<Result> findAllSpecific(Test test, Question question);
	
	public Result findById(long id);
	
	public double sumPoints(TestInstance testInstance, Test test);
	
	public void delete(long id);
	
}
