package finki.ask.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import finki.ask.api.model.ResponseStatus;
import finki.ask.api.model.ResponseWrapper;
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
import finki.ask.view.View;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin
public class TestsControllerAPI {
	
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
		
		Test test = testService.findByIdActive(id);
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
			testInstance.setEndTime(new Date(testInstance.getStartTime().getTime() + test.getDuration() * 60000l + 60000l)); // one extra minute
			testInstanceService.save(testInstance);
			
			session.setAttribute("testInstance", testInstance);
			session.setMaxInactiveInterval(test.getDuration() * 60 + 60); // one extra minute
		}
		else {
			Date now = new Date();
			if (now.compareTo(test.getEnd()) == 1) {
				responseWrapper.setDescription("The test is not active.");
				return null;
			}
			// load already answered questions		
		}
		
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		responseWrapper.setData(test);
		return responseWrapper;		
	}
	
	@ResponseBody
	@JsonView(View.CompleteAPI.class)
	@RequestMapping(value="/{id}", produces = "application/json", method = RequestMethod.POST)
	public ResponseWrapper postAnswer(@PathVariable long id, @RequestBody List<finki.ask.api.model.Answer> jsonAnswers, HttpServletRequest request, HttpServletResponse response) {
		
		Test test = testService.findById(id);
		HttpSession session = request.getSession(false);
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.ERROR);
		
		// validate session
		if (session == null) {
			responseWrapper.setDescription("Session does not exist.");
			return responseWrapper;
		}
		
		// validate date
		TestInstance testInstance = (TestInstance) session.getAttribute("testInstance");
		Date now = new Date();
		if (now.compareTo(test.getEnd()) == 1) {
			session.invalidate();
			responseWrapper.setDescription("Your time has expired.");
			return responseWrapper;
		}
		
		// wrong test, testInstance is not opened for this test
		if (id != testInstance.getTest().getId()) {
			responseWrapper.setDescription("You do not have permission to access this page.");
			return responseWrapper;
		}
		
		String finish = request.getParameter("finish");
		if (finish != null && finish.equals("true")) {
			// TODO
			// if test type is survey return how many questions are answered
			// else, return percentage (points)
			// show results
			responseWrapper.setResponseStatus(ResponseStatus.RESULTS);
			responseWrapper.setDescription("You have scored 1234 points out of 200.");
			responseWrapper.setData(new Long(55));
			return responseWrapper;
		}
		
		for (finki.ask.api.model.Answer jsonAnswer : jsonAnswers) {
			Question question = questionService.findById(jsonAnswer.getQuestionId());
			Answer answer = answerService.findById(jsonAnswer.getAnswerId());
			
			if (question == null) {
				responseWrapper.setDescription("Question  does not exist.");
				return responseWrapper;
			}
			
			if (answer == null) {
				responseWrapper.setDescription("Answer  does not exist.");
				return responseWrapper;
			}
			
			if (question == null || answer == null) {
				responseWrapper.setDescription("Question or answer does not exist.");
				return responseWrapper;
			}
			
			// validate that the question and the answers belong to the test
			if (!answer.getQuestion().equals(question) || !question.getTest().equals(test)) {
				responseWrapper.setDescription("Invalid question or answer.");
				return responseWrapper;
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
			
			// persist
			studentAnswerService.save(studentAnswer);
		}
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonAnswers));
		}
		catch (Exception e) {
			System.err.println("ObjectMapper Error");
		}
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		return responseWrapper;
	}
	
	@ResponseBody
	@JsonView(View.Public.class)
	@ExceptionHandler(Exception.class)
	public ResponseWrapper exceptionHandler(Exception ex) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.ERROR);
		responseWrapper.setDescription(ex.toString());
		System.err.println(ex.toString());
		ex.printStackTrace();
//		StringBuilder sb = new StringBuilder();
//	    for (StackTraceElement element : ex.getStackTrace()) {
//	        sb.append(element.toString());
//	        sb.append("\n");
//	    }		
		return responseWrapper;
	}
}
