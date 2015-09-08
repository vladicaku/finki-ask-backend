package finki.ask.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.TestInstance;

public interface TestInstanceRepository extends JpaRepository<TestInstance, Long>, TestInstanceRepositoryCustom {
}
