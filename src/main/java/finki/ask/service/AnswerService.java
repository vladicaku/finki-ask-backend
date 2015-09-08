package finki.ask.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Answer;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
public interface AnswerService {
	
	public Answer save(Answer answer);
	
	public Answer save(String json);
	
	public List<Answer> findAll();
	
	public Answer findById(long id);
	
	public void delete(long id);
	
	public Answer buildFromJson(String json);
}
