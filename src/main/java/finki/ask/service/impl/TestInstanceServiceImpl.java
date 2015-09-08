package finki.ask.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.TestInstance;
import finki.ask.repository.TestInstanceRepository;
import finki.ask.service.TestInstanceService;

@Service
public class TestInstanceServiceImpl implements TestInstanceService{

	@Autowired
	private TestInstanceRepository testInstanceRepository;
	
	@Override
	public TestInstance save(TestInstance testInstance) {
		return testInstanceRepository.save(testInstance);
	}

	@Override
	public List<TestInstance> findAll() {
		return testInstanceRepository.findAll();
	}

	@Override
	public TestInstance findById(long id) {
		return testInstanceRepository.findOne(id);
	}

	@Override
	public TestInstance findByUUID(UUID uuid) {
		return testInstanceRepository.findByUUID(uuid);
	}

	@Override
	public TestInstance findByIdAndUUID(long id, UUID uuid) {
		return testInstanceRepository.findByIdAndUUID(id, uuid);
	}

	@Override
	public void delete(long id) {
		testInstanceRepository.delete(id);
	}

}
