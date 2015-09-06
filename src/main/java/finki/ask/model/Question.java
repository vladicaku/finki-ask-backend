package finki.ask.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import finki.ask.view.View;

@Entity
@Table(name = "questions")
public class Question extends BaseEntity {

	@NotBlank
	@JsonView(View.Public.class)
	private String text;

	@NotNull
	@Enumerated
	@JsonView(View.Public.class)
	private QuestionType type;

	@NotNull
	@JsonView(View.Public.class)
	private int points;

	@NotNull
	@ManyToOne
	@JsonIgnore
	private Test test;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonDeserialize(as = LinkedHashSet.class)
	@JsonView(View.Public.class)
	@OrderBy(value="id")
	private Set<Answer> answers;

	public Question() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
