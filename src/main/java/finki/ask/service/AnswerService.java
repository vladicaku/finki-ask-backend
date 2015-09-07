package finki.ask.service;

import java.util.List;

import javax.transaction.Transactional;

import finki.ask.model.Answer;

@Transactional
public interface AnswerService {
	
	public Answer save(Answer answer);
	
	public Answer save(String json);
	
	public List<Answer> findAll();
	
	public Answer findById(long id);
	
	public void delete(long id);
	
	public Answer buildFromJson(String json);
}
