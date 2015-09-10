package finki.ask.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import finki.ask.model.StudentAnswer;
import finki.ask.model.User;
import finki.ask.repository.UserRepositoryCustom;

public class UserRepositoryImpl implements UserRepositoryCustom{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public User findByUsername(String username) {
		try {
			return (User) entityManager.createQuery("select u from User u where u.username = :username")
				.setParameter("username", username).getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}
}
