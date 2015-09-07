package finki.ask.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.TestInstance;
@Transactional
public interface TestInstanceRepository extends JpaRepository<TestInstance, Long>, TestInstanceRepositoryCustom {
}
