package finki.ask.service;

import java.util.List;
import java.util.UUID;

import finki.ask.model.Test;
import finki.ask.model.TestInstance;

public interface TestInstanceService {
	
	public TestInstance save(TestInstance testInstance);
	
	public List<TestInstance> findAll();
	
	public TestInstance findById(long id);
	
	public TestInstance findByUUID(UUID uuid);
	
	public TestInstance findByIdAndUUID(long id, UUID uuid);
	
	public void delete(long id);
}
