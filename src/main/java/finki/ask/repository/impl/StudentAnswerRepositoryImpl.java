package finki.ask.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.StudentAnswer;
import finki.ask.model.TestInstance;
import finki.ask.repository.StudentAnswerRepositoryCustom;

public class StudentAnswerRepositoryImpl implements StudentAnswerRepositoryCustom{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public StudentAnswer findSpecific(TestInstance testInstance, Question question, Answer answer) {
		return (StudentAnswer) entityManager.createQuery("select sa from StudentAnswer sa where sa.testInstance = :testInstance and sa.question = :question and sa.answer = :answer")
				.setParameter("testInstance", testInstance).setParameter("question", question).setParameter("answer", answer).getSingleResult();
	}

}
