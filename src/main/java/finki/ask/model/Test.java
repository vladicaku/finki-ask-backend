package finki.ask.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import finki.ask.json.view.View;

@Entity
@Table(name = "tests")
public class Test extends BaseEntity {

	@NotBlank
	@JsonView(View.Test.class)
	private String name;

	@NotNull
	@Enumerated
	@JsonView(View.Test.class)
	private TestType type;

	@DateTimeFormat(pattern="dd.MM.yyyy HH:mm")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	@NotNull
	@JsonView(View.Test.class)
	private Date dateCreated;

	@NotNull
	//@ManyToOne
	@JsonView(View.Test.class)
	private long creator;

	@NotNull
	@JsonView(View.Test.class)
	@DateTimeFormat(pattern="dd.MM.yyyy HH:mm")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private Date start;

	@NotNull
	@JsonView(View.Test.class)
	@DateTimeFormat(pattern="dd.MM.yyyy HH:mm")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private Date end;
	
	@NotNull
	@JsonView(View.Test.class)
	private int duration;

	@Column(name = "test_password")
	@NotBlank
	@JsonView(View.Test.class)
	private String password;
	
	@Transient
	private boolean isActive;

	@OneToMany(mappedBy = "test", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonDeserialize(as=LinkedHashSet.class)
	@JsonView(View.TestWithQuestions.class)
	private Set<Question> questions;

	public Test() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TestType getType() {
		return type;
	}

	public void setType(TestType type) {
		this.type = type;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
