package finki.ask.repository;

import java.util.List;

import finki.ask.model.Test;
import finki.ask.model.TestType;
import finki.ask.model.User;

public interface TestRepositoryCustom {
	public List<Test> findAllActive();
	public List<Test> findActiveByType(TestType type);
	public Test findActiveById(long id);
	public List<Test> findByName(String name);
	public List<Test> findByNameForUser(String name, User user);
	public List<Test> findByNameExceptForUser(String name, User user);
	public List<Test> findForUser(User user);
	public List<Test> findOtherTestsExceptForUser(User user);
}
