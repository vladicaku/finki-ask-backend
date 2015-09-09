package finki.ask.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.exception.InternalException;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;

@Transactional(propagation = Propagation.REQUIRED)
public interface SAEngineService {
	public void postAnswer(Test test, TestInstance testInstance, List<finki.ask.api.model.Answer> jsonAnswers, HttpSession session) throws Exception;
}
