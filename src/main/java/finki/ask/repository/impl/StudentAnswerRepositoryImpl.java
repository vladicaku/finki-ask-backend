package finki.ask.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;
import finki.ask.repository.StudentAnswerRepositoryCustom;

public class StudentAnswerRepositoryImpl implements StudentAnswerRepositoryCustom{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public StudentAnswer findSpecific(TestInstance testInstance, Test test, Question question, Answer answer) {
		try {
			return (StudentAnswer) entityManager.createQuery("select sa from StudentAnswer sa where sa.testInstance = :testInstance and sa.test = test and sa.question = :question and sa.answer = :answer")
				.setParameter("testInstance", testInstance).setParameter("test", test).setParameter("question", question).setParameter("answer", answer).getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test) {
		return entityManager.createQuery("select sa from StudentAnswer sa where sa.testInstance = :testInstance and sa.test = test")
				.setParameter("testInstance", testInstance).setParameter("test", test).getResultList();
	}
	
	@Override
	public List<StudentAnswer> findAllSpecific(TestInstance testInstance, Test test, Question question) {
		return entityManager.createQuery("select sa from StudentAnswer sa where sa.testInstance = :testInstance and sa.test = test and sa,question = question")
				.setParameter("testInstance", testInstance).setParameter("test", test).setParameter("question", question).getResultList();
	}

}
