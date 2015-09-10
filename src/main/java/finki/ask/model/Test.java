package finki.ask.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import finki.ask.view.View;

@Entity
@Table(name = "tests")
public class Test extends BaseEntity {

	@NotBlank
	@JsonView(View.Public.class)
	private String name;

	@NotNull
	@Enumerated
	@JsonView(View.Public.class)
	private TestType type;

	@NotNull
	@JsonView(View.Public.class)
	private boolean isPublic;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone="CET")
	@JsonView(View.Public.class)
	private Date dateCreated;

	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JsonView(View.SummaryAdmin.class)
	private User creator;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone="CET")
	@JsonView(View.Public.class)
	private Date start;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone="CET")
	@JsonView(View.Public.class)
	private Date end;

	@NotNull
	@JsonView(View.Public.class)
	private int duration;

	@NotBlank
	@Column(name = "test_password")
	@JsonView(View.CompleteAdmin.class)
	private String password;

	@Transient
	@JsonView(View.Public.class)
	private boolean isActive;

	@OneToMany(mappedBy = "test", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonDeserialize(as = LinkedHashSet.class)
	@JsonView(View.CompleteAPI.class)
	@OrderBy(value="id")
	private Set<Question> questions;
	
	@JsonView(View.Public.class)
	private int totalPoints;

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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
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

	public boolean isPublic() {
		return isPublic;
	}

	public void setOpen(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
}
