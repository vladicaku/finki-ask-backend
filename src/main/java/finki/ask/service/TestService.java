package finki.ask.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Test;
import finki.ask.model.TestType;

@Transactional(propagation = Propagation.REQUIRED)
public interface TestService {
	
	public Test save(Test test);
	
	public Test save(String json);;
	
	public List<Test> findAll();
	
	public Page<Test> findAll(Pageable pageable);
	
	public Test findById(long id);
	
	public Test findByIdActive(long id);
	
	public List<Test> findAllActive();
	
	public List<Test> findActiveByType(TestType type);
	
	public List<Test> findByName(String name);
	
	public List<Test> findByNameForUser(String name, Long id);
	
	public List<Test> findByNameExceptForUser(String name, Long id);
	
	public List<Test> findForUser(long id);
	
	public List<Test> findOtherTestsExceptForUser(long id);
	
	public void delete(long id);
	
	public Test buildFromJson(String json);
	
	public Test populateIsActive(Test test);
	
	public List<Test> populateIsActive(List<Test> tests);
}
