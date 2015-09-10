package finki.ask.repository.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import finki.ask.model.Test;
import finki.ask.model.TestType;
import finki.ask.model.User;
import finki.ask.repository.TestRepositoryCustom;

public class TestRepositoryImpl implements TestRepositoryCustom{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Test> findAllActive() {
		Date date = new Date();
		return entityManager.createQuery("select t from Test t where :date between t.start and t.end")
				.setParameter("date", date).getResultList();
	}
	
	@Override
	public Test findActiveById(long id) {
		Date date = new Date();
		return (Test) entityManager.createQuery("select t from Test t where :date between t.start and t.end and t.id = :id")
				.setParameter("date", date).setParameter("id", id).getSingleResult();

	}
	
	@Override
	public List<Test> findActiveByType(TestType type) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();
		return entityManager.createQuery("select t from Test t where :date between t.start and t.end and t.type = :type")
				.setParameter("date", date).setParameter("type", type).getResultList();
	
	}
	
	@Override
	public List<Test> findByName(String name) {
		return entityManager.createQuery("select t from Test t where t.name like :name").setParameter("name", name).getResultList();
	}
	
	/**
	 * @param name Question name
	 * @param id   Creator (user) id
	 */
	public List<Test> findByNameForUser(String name, User user) {
		return entityManager.createQuery("select t from Test t where t.name like :name and t.creator = :user").setParameter("name", name).setParameter("user", user).getResultList();
	}
	
	/**
	 * @param name Question name
	 * @param id Creator (user) id
	 */
	public List<Test> findByNameExceptForUser(String name, User user) {
		return entityManager.createQuery("select t from Test t where t.name like :name and t.creator != :user").setParameter("name", name).setParameter("user", user).getResultList();
	}

	@Override
	public List<Test> findForUser(User user) {
		Query query = entityManager.createQuery("select t from Test t where t.creator = :user");
		query.setParameter("user", user);
		return query.getResultList();
	}

	@Override
	public List<Test> findOtherTestsExceptForUser(User user) {
		Query query = entityManager.createQuery("select t from Test t where t.creator != :user");
		query.setParameter("user", user);
		return query.getResultList();
	}
	
}
