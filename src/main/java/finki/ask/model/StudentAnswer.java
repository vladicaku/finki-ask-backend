package finki.ask.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="student_answers")
public class StudentAnswer extends BaseEntity {
	
	@NotEmpty
	@ManyToOne
	private Test test;
	
	@NotEmpty
	@ManyToOne
	private Question question;
	
	@NotEmpty
	@ManyToOne
	private Answer answer;
	
	private boolean correct; // this is calculated only once, after the answer is sent

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

}
