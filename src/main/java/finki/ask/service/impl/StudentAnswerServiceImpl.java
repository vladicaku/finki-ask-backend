package finki.ask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;
import finki.ask.repository.StudentAnswerRepository;
import finki.ask.service.StudentAnswerService;

@Service
public class StudentAnswerServiceImpl implements StudentAnswerService{

	@Autowired
	private StudentAnswerRepository studentAnswerRepository;
	
	@Override
	public StudentAnswer save(StudentAnswer studentAnswer) {
		return studentAnswerRepository.save(studentAnswer);
	}

	@Override
	public List<StudentAnswer> findAll() {
		return studentAnswerRepository.findAll();
	}

	@Override
	public StudentAnswer findById(long id) {
		return (StudentAnswer) studentAnswerRepository.findOne(id);
	}
	
	@Override
	public StudentAnswer findSpecific(TestInstance testInstance, Test test, Question question, Answer answer) {
		return studentAnswerRepository.findSpecific(testInstance, test, question, answer);
	}

	@Override
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test) {
		return studentAnswerRepository.findAllSpecific(testInstance, test);
	}
	
	@Override
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test, Question question) {
		return studentAnswerRepository.findAllSpecific(testInstance, test, question);
	}
	
	@Override
	public List<StudentAnswer> findAllSpecific(Test test) {
		return studentAnswerRepository.findAllSpecific(test);
	}
	
	@Override
	public List<StudentAnswer> findAllSpecific(Test test, Question question) {
		return studentAnswerRepository.findAllSpecific(test, question);
	}
	
	@Override
	public void delete(long id) {
		studentAnswerRepository.delete(id);
	}

}
