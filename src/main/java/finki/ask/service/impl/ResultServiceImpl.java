package finki.ask.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finki.ask.model.Question;
import finki.ask.model.Result;
import finki.ask.model.ResultInfo;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;
import finki.ask.repository.ResultRepository;
import finki.ask.service.ResultService;
import finki.ask.service.StudentAnswerService;
import finki.ask.service.TestInstanceService;
import finki.ask.service.TestService;

@Service
public class ResultServiceImpl implements ResultService{

	@Autowired
	private ResultRepository resultRepository;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private StudentAnswerService studentAnswerService;
	
	@Autowired
	private TestInstanceService testInstanceService;  
	
	
	@Override
	public Result save(Result result) {
		return resultRepository.save(result); 
	}

	@Override
	public List<Result> findAll() {
		return resultRepository.findAll();
	}

	@Override
	public List<Result> findAllSpecific(TestInstance testInstance, Test test) {
		return resultRepository.findAllSpecific(testInstance, test);
	}

	@Override
	public Result findSpecific(TestInstance testInstance, Test test, Question question) {
		return resultRepository.findSpecific(testInstance, test, question);
	}

	@Override
	public List<Result> findAllSpecific(Test test) {
		return resultRepository.findAllSpecific(test);
	}

	@Override
	public List<Result> findAllSpecific(Test test, Question question) {
		return resultRepository.findAllSpecific(test, question);
	}

	@Override
	public Result findById(long id) {
		return resultRepository.findOne(id);
	}
	
	@Override
	public Result findSpecificOrCreateIfNotExist(TestInstance testInstance, Test test, Question question) {
		return resultRepository.findSpecificOrCreateIfNotExist(testInstance, test, question);
	}
	
	@Override
	public double sumPoints(TestInstance testInstance, Test test) {
		return resultRepository.sumPoints(testInstance, test);
	}

	@Override
	public void delete(long id) {
		resultRepository.delete(id);
	}

	
	
}
