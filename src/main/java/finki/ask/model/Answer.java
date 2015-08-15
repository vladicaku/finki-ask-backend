package finki.ask.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;

import finki.ask.json.view.View;

@Entity
@Table(name = "answers")
public class Answer extends BaseEntity {

	@NotBlank
	@JsonView(View.Test.class)
	private String text;

	@JsonView(View.Test.class)
	private boolean isCorrect;

	@NotNull
	@ManyToOne
	@JsonIgnore
	private Question question;

	public Answer() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
