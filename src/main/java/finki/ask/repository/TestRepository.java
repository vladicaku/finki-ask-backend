package finki.ask.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Test;

@Transactional()
public interface TestRepository extends JpaRepository<Test, Long>, TestRepositoryCustom{
	
	
}
