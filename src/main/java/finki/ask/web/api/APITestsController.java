package finki.ask.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.json.model.ResponseStatus;
import finki.ask.json.model.ResponseWrapper;
import finki.ask.json.view.View;
import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.QuestionType;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;
import finki.ask.model.TestType;
import finki.ask.service.AnswerService;
import finki.ask.service.QuestionService;
import finki.ask.service.StudentAnswerService;
import finki.ask.service.TestInstanceService;
import finki.ask.service.TestService;

@RestController
@RequestMapping("/api/tests")
public class APITestsController {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private StudentAnswerService studentAnswerService;
	
	@Autowired
	private TestInstanceService testInstanceService;
	
	@ResponseBody
	@JsonView(View.SummaryAPI.class)
	@RequestMapping(produces = "application/json", method = RequestMethod.GET)
	private List<Test> findAll(HttpServletRequest request) {
		if (request.getParameter("type") != null) {
			try { 
				TestType type = TestType.valueOf(request.getParameter("type"));
				return testService.findActiveByType(type);
			}
			catch (Exception ex) {
				return new ArrayList<>();
			}
		}
		else {
			return testService.findAllActive();
		}
	}
	
	@ResponseBody
	@JsonView(View.CompleteAPI.class)
	@RequestMapping(value="/{id}", produces = "application/json", method = RequestMethod.GET)
	public ResponseWrapper findById(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
		
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.ERROR);
		
		Test test = testService.findById(id);
		HttpSession session = request.getSession(true);
		TestInstance testInstance = (TestInstance) session.getAttribute("testInstance");
		String password = request.getParameter("password");
		
		if (test == null) {
			responseWrapper.setDescription("No such test.");
			return responseWrapper;
		}
		
		if (!test.getPassword().equals(password)) {
			responseWrapper.setDescription("Wrong password.");
			return responseWrapper;
		}
		
		if (testInstance == null || !testInstance.getTest().equals(test)) {
			testInstance = new TestInstance();
			testInstance.setTest(test);
			testInstance.setStartTime(new Date());
			testInstance.setEndTime(new Date(testInstance.getStartTime().getTime() + test.getDuration() * 60000l));
			testInstanceService.save(testInstance);
			
			session.setAttribute("testInstance", testInstance);
			session.setMaxInactiveInterval(test.getDuration());
		}
		else {
			Date now = new Date();
			if (now.compareTo(test.getEnd()) == 1) {
				responseWrapper.setDescription("The test is not active.");
				return null;
			}
			// load already answerd questions		
		}
		
		// smartphone fix
		for (Question q : test.getQuestions()) {
			for (Answer a : q.getAnswers()) {
				a.setQuestionID(q.getId());
			}
		}
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		responseWrapper.setData(test);
		return responseWrapper;		
	}
	
	@ResponseBody
	@JsonView(View.CompleteAPI.class)
	@RequestMapping(value="/{id}", produces = "application/json", method = RequestMethod.POST)
	public boolean submitAnswer(@PathVariable long id, @RequestBody finki.ask.json.model.Answer jsonAnswer, HttpServletRequest request, HttpServletResponse response) {
		
		Test test = testService.findById(id);
		HttpSession session = request.getSession(false);
		
		// validate session
		if (session == null) {
			return false;
		}
		
		
		// validate date
		TestInstance testInstance = (TestInstance) session.getAttribute("testInstance");
		Date now = new Date();
		if (now.compareTo(test.getEnd()) == 1) {
			session.invalidate();
			return false;
		}
		
		// wrong test, testInstance is not opened for this test
		if (id != testInstance.getTest().getId()) {
			return false;
		}
		
		// validate that question and answer belong in the test
		Question question = questionService.findById(jsonAnswer.getQuestionId());
		Answer answer = answerService.findById(jsonAnswer.getAnswerId());
		
		if (question == null || answer == null) {
			return false;
		}
		
		
		boolean flag = false;
		for (Question q : test.getQuestions()) {
			if (q.equals(question)) {
				flag = true;
				break;
			}
		}
		
		if (!flag) {
			return false;
		}
		
		
		flag = false;
		for (Answer a : question.getAnswers()) {
			if (a.equals(answer)) {
				flag = true;
				break;
			}
		}
		
		if (!flag) {
			return false;
		}
		
		
		// try to load an existing answer if exist or create a new one if don't
		StudentAnswer studentAnswer = studentAnswerService.findSpecific(testInstance, question, answer);
		
		if (studentAnswer == null) {
			studentAnswer = new StudentAnswer();
			studentAnswer.setQuestion(question);
			studentAnswer.setAnswer(answer);
			studentAnswer.setTestInstance(testInstance);
		}
		
		
		// calculate isCorrect flag (faster graph calculations)
		if (jsonAnswer.getText() != null) {
			studentAnswer.setText(jsonAnswer.getText());
		}
		
		if (question.getType() == QuestionType.TEXT) {
			if (question.getText() == null) {
				studentAnswer.setCorrect(false);
			}
			else {
				String correctAnswer = question.getText().trim().toLowerCase();
				String newAnswer = jsonAnswer.getText().trim().toLowerCase(); 
				studentAnswer.setCorrect(correctAnswer.equals(newAnswer));
			}
		}
		else {
			// TODO: check type 
			studentAnswer.setCorrect(answer.isCorrect() == jsonAnswer.isChecked());
		}
		
		// persist
		studentAnswerService.save(studentAnswer);
		
		return true;
	}
}
