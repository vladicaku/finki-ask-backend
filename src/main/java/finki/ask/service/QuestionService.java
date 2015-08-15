package finki.ask.service;

import java.util.List;

import finki.ask.model.Question;

public interface QuestionService {
	
	public Question save(Question question);
	
	public Question save(String json);
	
	public List<Question> findAll();
	
	public Question findById(long id);
	
	public void delete(long id);
	
	public Question buildFromJson(String json);
}
