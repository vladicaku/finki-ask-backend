package finki.ask.repository;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.StudentAnswer;
import finki.ask.model.TestInstance;

public interface StudentAnswerRepositoryCustom {
	public StudentAnswer findSpecific(TestInstance testInstance, Question question, Answer answer);
}
