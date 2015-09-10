package finki.ask.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;

import finki.ask.view.View;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@NotBlank
	@Column(unique=true)
	@JsonView(View.Public.class)
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Column(unique=true)
	@JsonView(View.Public.class)
	private String email;
	
	@NotBlank
	@JsonView(View.Public.class)
	private String firstAndLastName;
	
	@NotNull
	@JsonView(View.Public.class)
	private boolean enabled;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonView(View.Public.class)
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
