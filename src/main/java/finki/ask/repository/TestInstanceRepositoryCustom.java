package finki.ask.repository;

import java.util.UUID;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.TestInstance;


@Transactional(isolation=Isolation.READ_COMMITTED)
public interface TestInstanceRepositoryCustom {
	public TestInstance findByUUID(UUID uuid);
	public TestInstance findByIdAndUUID(long id, UUID uuid);
}
