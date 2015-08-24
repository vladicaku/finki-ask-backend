package finki.ask.repository;

import java.util.List;

import finki.ask.model.Test;
import finki.ask.model.TestType;

public interface TestRepositoryCustom {
	public List<Test> findAllActive();
	public List<Test> findActiveByType(TestType type);
	public List<Test> findByName(String name);
	public List<Test> findByNameForUser(String name, Long id);
	public List<Test> findByNameExceptForUser(String name, Long id);
	public List<Test> findForUser(Long id);
	public List<Test> findOtherTestsExceptForUser(Long id);
}
