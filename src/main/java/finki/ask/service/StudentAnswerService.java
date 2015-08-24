package finki.ask.service;

import java.util.List;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.StudentAnswer;
import finki.ask.model.TestInstance;

public interface StudentAnswerService {
	
	public StudentAnswer save(StudentAnswer studentAnswer);
	
	public List<StudentAnswer> findAll();
	
	public StudentAnswer findSpecific(TestInstance testInstance, Question question, Answer answer);
	
	public StudentAnswer findById(long id);
	
	public void delete(long id);
	
}
