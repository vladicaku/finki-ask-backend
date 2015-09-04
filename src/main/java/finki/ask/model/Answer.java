package finki.ask.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;

import finki.ask.view.View;

@Entity
@Table(name = "answers")
public class Answer extends BaseEntity {

	@NotNull
	@JsonView(View.Public.class)
	private String text;
	
	@JsonView(View.CompleteAdmin.class)
	private String correct;
	
	@JsonView(View.CompleteAdmin.class)
	private boolean isChecked;

	@NotNull
	@ManyToOne
	@JsonIgnore
	private Question question;

	// smartphone fix
	@Transient
	@JsonView(View.CompleteAPI.class)
	//@JsonInclude(Include.NON_NULL)
	private long questionId;

	public Answer() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public long getQuestionId() {
		return question.getId();
	}

	public void setQuestionId(long questionID) {
		this.questionId = questionID;
	}
	
}
