package finki.ask.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "results")
public class Result extends  BaseEntity {
	
	@NotNull
	@ManyToOne
	private Test test;

	@NotNull
	@ManyToOne
	private Question question;
	
	@NotNull
	@ManyToOne
	@JsonIgnore
	private TestInstance testInstance;
	
	@NotNull
	private long totalCorrect;
	
	@NotNull
	private double totalPoints;
	
	@NotNull
	private long answeredCorrect;

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

	public TestInstance getTestInstance() {
		return testInstance;
	}

	public void setTestInstance(TestInstance testInstance) {
		this.testInstance = testInstance;
	}

	public long getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(long totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	public long getAnsweredCorrect() {
		return answeredCorrect;
	}

	public void setAnsweredCorrect(long answeredCorrect) {
		this.answeredCorrect = answeredCorrect;
	}

	public double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}
	
	
}
