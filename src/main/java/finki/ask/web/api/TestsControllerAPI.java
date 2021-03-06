package finki.ask.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import finki.ask.view.View;

@CrossOrigin
@RestController
@RequestMapping("/api/tests")
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
	
	@Autowired
	private ResultService resultService;
	
	@ResponseBody
	@JsonView(View.SummaryAPI.class)
	@RequestMapping(produces = "application/json", method = RequestMethod.GET)
	public List<Test> findAll(HttpServletRequest request) {
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
	
	private void findByIdValidator(Test test, String password) throws Exception {
		if (test == null) {
			throw new Exception("No such test.");
		}
		
		if (password == null || !test.getPassword().equals(password)) {
			throw new Exception("Wrong password.");
		}
		
	}
	
	@ResponseBody
	@JsonView(View.CompleteAPI.class)
	@RequestMapping(value="/{id}", produces = "application/json", method = RequestMethod.GET)
	public ResponseWrapper findById(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ResponseWrapper responseWrapper = new ResponseWrapper();

		Test test = testService.findByIdActive(id);
		HttpSession session = request.getSession(true);
		TestInstance testInstance = (TestInstance) session.getAttribute("testInstance");
		String password = request.getParameter("password");
		
		findByIdValidator(test, password);
		
		testInstance = new TestInstance();
		testInstance.setTest(test);
		testInstance.setStartTime(new Date());
		testInstance.setEndTime(new Date(testInstance.getStartTime().getTime() + test.getDuration() * 60000l + 60000l)); // one extra minute
		testInstanceService.save(testInstance);
		
		// TODO
		// create new session
		session.setAttribute("testInstance", testInstance);
		session.setMaxInactiveInterval(test.getDuration() * 60 + 60); // one extra minute

		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		responseWrapper.setData(test);
		return responseWrapper;		
	}
	
	@ResponseBody
	@JsonView(View.CompleteAPI.class)
	@RequestMapping(value="/{id}", produces = "application/json", method = RequestMethod.POST)
	public ResponseWrapper postAnswer(@PathVariable long id, @RequestBody List<finki.ask.api.model.Answer> jsonAnswers, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Test test = testService.findById(id);
		HttpSession session = request.getSession(false);
		ResponseWrapper responseWrapper = new ResponseWrapper();
		
		// validate session
		if (session == null) {
			throw new Exception("Session does not exist.");
		}
		
		// validate date
		TestInstance testInstance = (TestInstance) session.getAttribute("testInstance");
		Date now = new Date();
		if (now.compareTo(test.getEnd()) == 1) {
			session.invalidate();
			throw new Exception("Your time has expired.");
		}
		
		// wrong test, testInstance is not opened for this test
		if (id != testInstance.getTest().getId()) {
			throw new Exception("You do not have permission to access this page.");
		}
		
		String finish = request.getParameter("finish");
		if (finish != null && finish.equals("true")) {
			responseWrapper.setResponseStatus(ResponseStatus.RESULTS);
			int totalQuestions = test.getQuestions().size();
			// TODO
			// agregate this call
			int totalQuestionAnswred = resultService.findAllSpecific(testInstance, test).size(); // 
			
			if (test.getType() == TestType.SURVEY) {
				responseWrapper.setData(new Integer(totalQuestionAnswred / totalQuestions));
				responseWrapper.setDescription("Thank you for completing our survey.");
			}
			else {
				double myPoints = resultService.sumPoints(testInstance, test);
				double totalPoints = test.getTotalPoints();
				responseWrapper.setData(new Integer(Math.min((int) (myPoints / totalPoints * 100), 100)));
				responseWrapper.setDescription(String.format("You have scored %.0f of total %d points. Answred %d / %d. ", myPoints, test.getTotalPoints(), totalQuestionAnswred, totalQuestions));
			}
			
			return responseWrapper;
		}
		
		
		Result result = null;
		Question question = null;
		long totalCorrect = 1;
		long answeredCorrect = 0;
		long totalPoints = 0;
		
		if (jsonAnswers.size() != 0) {
			question = questionService.findById(jsonAnswers.get(0).getQuestionId());
			result = resultService.findSpecific(testInstance, test, question);
			totalCorrect = question.getType() == QuestionType.MULTIPLE ? 0 : 1;
			
			if (result == null) {
				System.out.println(">>>>>>>>>>>>>>> NEW");
				result = new Result();
				result.setTest(test);
				result.setQuestion(question);
				result.setTestInstance(testInstance);
				result.setTotalCorrect(totalCorrect);
				//result = resultService.save(result);
				Thread.sleep(1000 * 20);
			}
		}
		
		for (finki.ask.api.model.Answer jsonAnswer : jsonAnswers) {
			//Question question = questionService.findById(jsonAnswer.getQuestionId());
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
			
			// persist answer
			studentAnswerService.save(studentAnswer);
			
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
		
		// calculate correct out of total correct
		// calculate points 
		result.setTotalCorrect(totalCorrect);
		result.setAnsweredCorrect(answeredCorrect < 0 ? 0 : answeredCorrect);
		result.setTotalPoints(result.getAnsweredCorrect() * 1.0 / result.getTotalCorrect() * question.getPoints());

		// persist result
		resultService.save(result);
		
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
