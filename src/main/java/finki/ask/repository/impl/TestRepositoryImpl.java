package finki.ask.repository.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Test;
import finki.ask.model.TestType;
import finki.ask.repository.TestRepositoryCustom;

@Transactional
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
	public Test findByIdActive(long id) {
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
	public List<Test> findByNameForUser(String name, Long id) {
		return entityManager.createQuery("select t from Test t where t.name like :name and t.creator = :id").setParameter("name", name).setParameter("id", id).getResultList();
	}
	
	/**
	 * @param name Question name
	 * @param id Creator (user) id
	 */
	public List<Test> findByNameExceptForUser(String name, Long id) {
		return entityManager.createQuery("select t from Test t where t.name like :name and t.creator != :id").setParameter("name", name).setParameter("id", id).getResultList();
	}

	@Override
	public List<Test> findForUser(Long id) {
		Query query = entityManager.createQuery("select t from Test t where t.creator = :id");
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public List<Test> findOtherTestsExceptForUser(Long id) {
		Query query = entityManager.createQuery("select t from Test t where t.creator != :id");
		query.setParameter("id", id);
		return query.getResultList();
	}
	
}
