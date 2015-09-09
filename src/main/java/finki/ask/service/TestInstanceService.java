package finki.ask.service;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.TestInstance;

@Transactional(propagation = Propagation.REQUIRED)
public interface TestInstanceService {
	
	public TestInstance save(TestInstance testInstance);
	
	public List<TestInstance> findAll();
	
	public TestInstance findById(long id);
	
	public TestInstance findByUUID(UUID uuid);
	
	public TestInstance findByIdAndUUID(long id, UUID uuid);
	
	public void delete(long id);
}
