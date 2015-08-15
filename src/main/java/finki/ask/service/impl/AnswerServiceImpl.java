package finki.ask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.json.view.View;
import finki.ask.model.Answer;
import finki.ask.repository.AnswerRepository;
import finki.ask.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
	@JsonView(View.TestWithQuestions.class)
	private AnswerRepository answerRepository;

	@Override
	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}

	@Override
	public Answer save(String json) {
		return null;
	}

	@Override
	public List<Answer> findAll() {
		return answerRepository.findAll();
	}

	@Override
	public Answer findById(long id) {
		return answerRepository.findOne(id);
	}

	@Override
	public void delete(long id) {
		answerRepository.delete(id);
	}

	@Override
	public Answer buildFromJson(String json) {
		return null;
	}

}
