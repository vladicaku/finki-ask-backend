package finki.ask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.json.view.View;
import finki.ask.model.Question;
import finki.ask.repository.QuestionRepository;
import finki.ask.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	@JsonView(View.TestWithQuestions.class)
	private QuestionRepository questionRepository;
	
	@Override
	public Question save(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public Question save(String json) {
		return null;
	}


	@Override
	public List<Question> findAll() {
		return questionRepository.findAll();
	}

	@Override
	public Question findById(long id) {
		return questionRepository.findOne(id);
	}

	@Override
	public void delete(long id) {
		questionRepository.delete(id);
	}

	@Override
	public Question buildFromJson(String json) {
		return null;
	}

	
}
