package finki.ask.repository.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import finki.ask.model.TestInstance;
import finki.ask.repository.TestInstanceRepositoryCustom;

public class TestInstanceRepositoryImpl implements TestInstanceRepositoryCustom{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TestInstance findByUUID(UUID uuid) {
		return (TestInstance) entityManager.createQuery("select ti from TestInstance ti where ti.uuid = :uuid").setParameter("uuid", uuid).getSingleResult();
	}

	@Override
	public TestInstance findByIdAndUUID(long id, UUID uuid) {
		return (TestInstance) entityManager.createQuery("select ti from TestInstance ti where ti.uuid = :uuid and ti.id = :id").setParameter("uuid", uuid).setParameter("id", id).getSingleResult();
	}

}
