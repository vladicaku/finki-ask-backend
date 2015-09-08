package finki.ask.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Test;
import finki.ask.model.TestType;

@Transactional()
public interface TestRepositoryCustom {
	public List<Test> findAllActive();
	public List<Test> findActiveByType(TestType type);
	public Test findByIdActive(long id);
	public List<Test> findByName(String name);
	public List<Test> findByNameForUser(String name, Long id);
	public List<Test> findByNameExceptForUser(String name, Long id);
	public List<Test> findForUser(Long id);
	public List<Test> findOtherTestsExceptForUser(Long id);
}
