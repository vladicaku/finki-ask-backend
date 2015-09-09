package finki.ask.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "student_answers")
public class StudentAnswer extends BaseEntity {

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Test test;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Question question;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Answer answer;

	private String text;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private TestInstance testInstance;

	private boolean correct; // this is calculated only once, after the answer
								// is sent

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TestInstance getTestInstance() {
		return testInstance;
	}

	public void setTestInstance(TestInstance testInstance) {
		this.testInstance = testInstance;
	}

}
