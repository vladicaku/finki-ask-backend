package finki.ask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import finki.ask.model.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{

}
