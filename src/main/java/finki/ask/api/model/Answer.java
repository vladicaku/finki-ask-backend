package finki.ask.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Answer {
	
	private long questionId;
	
	@JsonProperty("id")
	private long answerId;
	
	@JsonProperty("isChecked")
	private boolean isChecked;
	
	private String text;

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(long answerId) {
		this.answerId = answerId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
