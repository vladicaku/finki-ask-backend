package finki.ask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.TestInstance;

public interface TestInstanceRepository extends JpaRepository<TestInstance, Long>, TestInstanceRepositoryCustom {
}
