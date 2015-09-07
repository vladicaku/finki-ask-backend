package finki.ask.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.Result;
import finki.ask.model.StudentAnswer;
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
		return (double) entityManager.createQuery("select sum(r.points) from Result r where r.testInstance = :testInstance and r.test = :test")
				.setParameter("testInstance", testInstance).setParameter("test", test).getSingleResult();
	}

}
