package finki.ask.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Question;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
public interface QuestionService {
	
	public Question save(Question question);
	
	public Question save(String json);
	
	public List<Question> findAll();
	
	public Question findById(long id);
	
	public void delete(long id);
	
	public Question buildFromJson(String json);
}
