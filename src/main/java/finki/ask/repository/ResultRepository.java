package finki.ask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.Result;

public interface ResultRepository extends JpaRepository<Result, Long>, ResultRepositoryCustom{

}
