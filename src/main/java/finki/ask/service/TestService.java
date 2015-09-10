package finki.ask.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Test;
import finki.ask.model.TestType;
import finki.ask.model.User;

@Transactional(propagation = Propagation.REQUIRED)
public interface TestService {
	
	public Test save(Test test);
	
	public Test save(String json);;
	
	public List<Test> findAll();
	
	public Page<Test> findAll(Pageable pageable);
	
	public Test findById(long id);
	
	public Test findActiveById(long id);
	
	public List<Test> findAllActive();
	
	public List<Test> findActiveByType(TestType type);
	
	public List<Test> findByName(String name);
	
	public List<Test> findByNameForUser(String name, User user);
	
	public List<Test> findByNameExceptForUser(String name, User user);
	
	public List<Test> findForUser(User user);
	
	public List<Test> findOtherTestsExceptForUser(User user);
	
	public void delete(long id);
	
	public Test buildFromJson(String json);
	
	public Test populateIsActive(Test test);
	
	public List<Test> populateIsActive(List<Test> tests);
}
