package finki.ask.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.Test;
import finki.ask.repository.TestRepositoryCustom;

public class TestRepositoryImpl implements TestRepositoryCustom{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Test> findByName(String name) {
		return entityManager.createQuery("select t from Test t where t.name like :name").setParameter("name", name).getResultList();
	}
	
	/**
	 * @param name
	 *            Question name
	 * @param id
	 *            Creator (user) id
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
