package finki.ask.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Answer;
import finki.ask.model.User;

@Transactional(propagation = Propagation.REQUIRED)
public interface UserService {
	
	public User save(User User);
	
	public List<User> findAll();
	
	public User findById(long id);
	
	public User findByUsername(String username);
	
	public void delete(long id);

}
