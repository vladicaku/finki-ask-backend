package finki.ask.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "test_instances")
public class TestInstance extends BaseEntity {
	
	// TODO
	// check date for timezone errors 
	
	@NotNull
	@ManyToOne
	private Test test;
	
	@ManyToOne
	private User user;
	
	private long studentIndex;
	
	@NotNull
	private Date startTime;
	
	@NotNull
	private Date endTime;
	
	private int quasiLocker;

	public TestInstance() {
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getStudentIndex() {
		return studentIndex;
	}

	public void setStudentIndex(long studentIndex) {
		this.studentIndex = studentIndex;
	}

	public int getQuasiLocker() {
		return quasiLocker;
	}

	public void setQuasiLocker(int quasiLocker) {
		this.quasiLocker = quasiLocker;
	}
}
