package finki.ask.model;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.view.View;

public class ResultInfo {
	
	@JsonView(View.Public.class)
	Question question;
	
	@JsonView(View.Public.class)
	int total;
	
	@JsonView(View.Public.class)
	int correct;
	
	@JsonView(View.Public.class)
	int incorrect;
	
	@JsonView(View.Public.class)
	int partialCorrect;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getTotal() {
		return correct + partialCorrect + incorrect;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(int incorrect) {
		this.incorrect = incorrect;
	}

	public int getPartialCorrect() {
		return partialCorrect;
	}

	public void setPartialCorrect(int partialCorrect) {
		this.partialCorrect = partialCorrect;
	}
	
	
}
