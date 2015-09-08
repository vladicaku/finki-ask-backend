package finki.ask.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;

import finki.ask.model.Question;
import finki.ask.model.Result;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;
import finki.ask.repository.ResultRepositoryCustom;

public class ResultRepositoryImpl implements ResultRepositoryCustom{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Result> findAllSpecific(Test test) {
		return entityManager.createQuery("select r from Result r where r.test = :test")
				.setParameter("test", test).getResultList();
	}

	@Override
	public List<Result> findAllSpecific(Test test, Question question) {
		return entityManager.createQuery("select r from Result r where r.test = :test and r.question = :question")
				.setParameter("test", test).setParameter("question", question).getResultList();
	}

	@Override
	public List<Result> findAllSpecific(TestInstance testInstance, Test test) {
		return entityManager.createQuery("select r from Result r where r.testInstance = :testInstance and r.test = :test")
				.setParameter("testInstance", testInstance).setParameter("test", test).getResultList();
	}

	@Override
	public Result findSpecific(TestInstance testInstance, Test test, Question question) {
		try {
			return (Result) entityManager.createQuery("select r from Result r where r.testInstance = :testInstance and r.test = :test and r.question = :question")
					.setParameter("test", test).setParameter("testInstance", testInstance).setParameter("question", question).getSingleResult();
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	@Override
	public double sumPoints(TestInstance testInstance, Test test) {
		try {
			return (double) entityManager.createQuery("select SUM(r.totalPoints) from Result r where r.testInstance = :testInstance and r.test = :test")
					.setParameter("testInstance", testInstance).setParameter("test", test).getSingleResult();
		}
		catch (Exception ex) {
			return 0;
		}
	}
	
	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public Result findSpecificOrCreateIfNotExist(TestInstance testInstance, Test test, Question question) {
		try {
			return (Result) entityManager.createQuery("select r from Result r where r.testInstance = :testInstance and r.test = :test and r.question = :question")
					.setParameter("test", test).setParameter("testInstance", testInstance).setParameter("question", question).getSingleResult();
		}
		catch (Exception ex) {
			Result result = new Result();
			result.setTestInstance(testInstance);
			result.setTest(test);
			result.setQuestion(question);
			entityManager.merge(result);
			return (Result) entityManager.createQuery("select r from Result r where r.testInstance = :testInstance and r.test = :test and r.question = :question")
					.setParameter("test", test).setParameter("testInstance", testInstance).setParameter("question", question).getSingleResult();
		}
	}

}
