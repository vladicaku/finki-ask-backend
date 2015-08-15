package finki.ask.repository;

import java.util.List;

import finki.ask.model.Test;

public interface TestRepositoryCustom {
	public List<Test> findByName(String name);
	public List<Test> findByNameForUser(String name, Long id);
	public List<Test> findByNameExceptForUser(String name, Long id);
	public List<Test> findForUser(Long id);
	public List<Test> findOtherTestsExceptForUser(Long id);
}
