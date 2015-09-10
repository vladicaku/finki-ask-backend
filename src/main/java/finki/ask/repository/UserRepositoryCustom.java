package finki.ask.repository;

import finki.ask.model.User;

public interface UserRepositoryCustom {
	public User findByUsername(String username);
}
