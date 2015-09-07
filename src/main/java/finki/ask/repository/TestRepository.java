package finki.ask.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.Test;

@Transactional
public interface TestRepository extends JpaRepository<Test, Long>, TestRepositoryCustom{
	
	
}
