package finki.ask.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.Result;
@Transactional
public interface ResultRepository extends JpaRepository<Result, Long>, ResultRepositoryCustom{

}
