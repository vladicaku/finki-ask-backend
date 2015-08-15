package finki.ask.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	private int type;

	public User() {
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
