package finki.ask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@NotBlank
	@Column(unique=true)
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Column(unique=true)
	private String email;
	
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
