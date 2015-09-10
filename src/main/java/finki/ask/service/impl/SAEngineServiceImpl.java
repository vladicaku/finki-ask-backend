package finki.ask.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.exception.InternalException;
import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.QuestionType;
import finki.ask.model.Result;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;
import finki.ask.model.TestType;
import finki.ask.service.AnswerService;
import finki.ask.service.QuestionService;
import finki.ask.service.ResultService;
import finki.ask.service.StudentAnswerService;
import finki.ask.service.TestInstanceService;
import finki.ask.service.TestService;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
public class SAEngineServiceImpl implements finki.ask.service.SAEngineService{
	
	@Autowired
	ResultService resultService;
	
	@Autowired
	TestInstanceService testInstanceService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	StudentAnswerService studentAnswerService;
	
	@Autowired
	TestService testService;
	
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void postAnswer(Test test, TestInstance testInstance, List<finki.ask.api.model.Answer> jsonAnswers, HttpSession session) throws InternalException, InterruptedException{
		
		//validate test
		if (test == null) {
			throw new InternalException("Test does not exist");
		}
		
		// validate TestInsantce
		if (testInstance == null) {
			throw new InternalException("You do not have permission to access this page. (Invalid session)");
		}
		
		// validate date
		Date now = new Date();
		if (now.compareTo(test.getEnd()) == 1) {
			session.invalidate();
			throw new InternalException("Your time has expired.");
		}
				
		// wrong test, testInstance is not opened for this test
		if (!testInstance.getTest().equals(test)) {
			throw new InternalException("You do not have permission to access this page. (2)");
		}
		
		Result result = null;
		Question question = null;
		long totalCorrect = 1;
		long answeredCorrect = 0;
	
		
		if (jsonAnswers.size() != 0) {
			// lock transaction
			//testInstance.setQuasiLocker(testInstance.getQuasiLocker() < 5 ? 10 : 0);
			testInstanceService.save(testInstance);
			//return;
			//Thread.sleep(1000 * 20);
			
			question = questionService.findById(jsonAnswers.get(0).getQuestionId());
			result = resultService.findSpecific(testInstance, test, question);
			totalCorrect = question.getType() == QuestionType.MULTIPLE ? 0 : 1;
			
			if (result == null) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>> NEW " + testInstance.getId());
				result = new Result();
				result.setTest(test);
				result.setQuestion(question);
				result.setTestInstance(testInstance);
				result.setTotalCorrect(totalCorrect);
				Thread.sleep(1000 * 10);
			}
		}
		
		for (finki.ask.api.model.Answer jsonAnswer : jsonAnswers) {
			Answer answer = answerService.findById(jsonAnswer.getAnswerId());
			
			// validate
			validate(test, question, answer);
			
			// count total correct for type Multiple for the result
			if (question.getType() == QuestionType.MULTIPLE && answer.isChecked()) {
				totalCorrect++;
			}
			
			// try to load an existing answer if exist or create a new one if don't
			StudentAnswer studentAnswer = studentAnswerService.findSpecific(testInstance, test, question, answer);
			
			if (studentAnswer == null) {
				studentAnswer = new StudentAnswer();
				studentAnswer.setQuestion(question);
				studentAnswer.setAnswer(answer);
				studentAnswer.setTest(test);
				studentAnswer.setTestInstance(testInstance);
			}
		
			// calculate isCorrect flag ( reason: faster graph calculations)
			if (test.getType() != TestType.SURVEY) {
				if (question.getType() == QuestionType.TEXT) {
					if (jsonAnswer.getText() == null) {
						studentAnswer.setCorrect(false);
					}
					else {
						String correctAnswer = answer.getCorrect().trim().toLowerCase();
						String newAnswer = jsonAnswer.getText().trim().toLowerCase();
						studentAnswer.setCorrect(correctAnswer.equals(newAnswer));
						studentAnswer.setText(jsonAnswer.getText());
					}
				}
				else if (question.getType() == QuestionType.SINGLE || question.getType() == QuestionType.MULTIPLE) {
					studentAnswer.setCorrect(answer.isChecked() == jsonAnswer.isChecked());
				}
				else if (question.getType() == QuestionType.RANGE) {
					studentAnswer.setText(jsonAnswer.getText());
					String rangeCorrect = studentAnswer.getText().split(":")[2];
					studentAnswer.setCorrect(answer.getCorrect().equals(rangeCorrect));
				}
			}
			
			// persist answer
			studentAnswerService.save(studentAnswer);
			
			if (test.getType() == TestType.SURVEY) {
				continue;
			}
			
			// count answered correct for the result
			if (studentAnswer.isCorrect() && (question.getType() == QuestionType.TEXT || question.getType() == QuestionType.RANGE)) {
				answeredCorrect++;
			}
			else if (!studentAnswer.isCorrect() && (question.getType() == QuestionType.TEXT || question.getType() == QuestionType.RANGE)) {
				answeredCorrect--;
			}
			else if (studentAnswer.isCorrect() && answer.isChecked()) {
				answeredCorrect++;
			}
			else if (!studentAnswer.isCorrect() && !answer.isChecked()){
				answeredCorrect--;
			}
		}
		
		// calculate correct out of total correct and points
		result.setTotalCorrect(totalCorrect);
		result.setAnsweredCorrect(answeredCorrect < 0 ? 0 : answeredCorrect);
		result.setTotalPoints(result.getAnsweredCorrect() * 1.0 / result.getTotalCorrect() * question.getPoints());

		// make universal for Survey
		if (test.getType() == TestType.SURVEY) {
			result.setTotalPoints(0);
			result.setAnsweredCorrect(0);
			result.setTotalCorrect(0);
		}
		// persist result
		resultService.save(result);
		
	}
	
	private void validate(Test test, Question question, Answer answer) throws InternalException {
		if (question == null) {
			throw new InternalException("Question  does not exist.");
		}
		
		if (answer == null) {
			throw new InternalException("Answer  does not exist.");
		}
		
		if (question == null || answer == null) {
			throw new InternalException("Question or answer does not exist.");
		}
		
		// validate that the question and the answers belong to the test
		if (!answer.getQuestion().equals(question) || !question.getTest().equals(test)) {
			throw new InternalException("Invalid question or answer.");
		}
	}
}
