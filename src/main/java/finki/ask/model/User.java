package finki.ask.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.sun.istack.NotNull;

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
	
	@NotBlank
	private String firstAndLastName;
	
	@NotNull
	private boolean enabled;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<UserRole> userRole = new HashSet<UserRole>(0);

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstAndLastName() {
		return firstAndLastName;
	}

	public void setFirstAndLastName(String firstAndLastName) {
		this.firstAndLastName = firstAndLastName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
}
