package finki.ask.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

import com.sun.istack.NotNull;

@Entity
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = { "role", "user_id" }))
public class UserRole extends BaseEntity{
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@NotBlank
	private String role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
