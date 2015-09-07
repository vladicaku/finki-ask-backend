package finki.ask.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import finki.ask.model.TestInstance;

@Transactional
public interface TestInstanceRepositoryCustom {
	public TestInstance findByUUID(UUID uuid);
	public TestInstance findByIdAndUUID(long id, UUID uuid);
}
