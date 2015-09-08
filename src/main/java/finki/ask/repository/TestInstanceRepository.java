package finki.ask.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.TestInstance;


@Transactional(isolation=Isolation.READ_COMMITTED)
public interface TestInstanceRepository extends JpaRepository<TestInstance, Long>, TestInstanceRepositoryCustom {
	
	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	TestInstance findOne(Long arg0);
	
	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	<S extends TestInstance> S save(S arg0);
}
