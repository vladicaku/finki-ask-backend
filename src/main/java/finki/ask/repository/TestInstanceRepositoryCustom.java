package finki.ask.repository;

import java.util.UUID;

import finki.ask.model.TestInstance;

public interface TestInstanceRepositoryCustom {
	public TestInstance findByUUID(UUID uuid);
	public TestInstance findByIdAndUUID(long id, UUID uuid);
}
