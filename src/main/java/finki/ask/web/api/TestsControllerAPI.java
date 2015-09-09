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
import finki.ask.exception.InternalException;
import finki.ask.model.Test;
import finki.ask.model.TestInstance;
import finki.ask.model.TestType;
import finki.ask.service.ResultService;
import finki.ask.service.SAEngineService;
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
	private TestInstanceService testInstanceService;
	
	@Autowired
	private SAEngineService saEngine;
	
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

	@ResponseBody
	@JsonView(View.CompleteAPI.class)
	@RequestMapping(value="/{id}", produces = "application/json", method = RequestMethod.GET)
	public ResponseWrapper findById(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseWrapper responseWrapper = new ResponseWrapper();

		Test test = testService.findByIdActive(id);
		HttpSession session = request.getSession(true);
		TestInstance testInstance = (TestInstance) session.getAttribute("testInstance");
		String password = request.getParameter("password");
		
		// validate test
		if (test == null) {
			throw new InternalException("No such test.");
		}
		
		// validate password
		if (password == null || !test.getPassword().equals(password)) {
			throw new InternalException("Wrong password.");
		}
		
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
			throw new InternalException("Session does not exist.");
		}
		
		TestInstance testInstance = (TestInstance) session.getAttribute("testInstance");
		
		// validate TestInsantce
		if (testInstance == null) {
			throw new InternalException("You do not have permission to access this page. (Invalid session)");
		}
		
		// validate date
		Date now = new Date();
		if (now.compareTo(test.getEnd()) == 1) {
			session.invalidate();
			throw new Exception("Your time has expired.");
		}
		
		// wrong test, testInstance is not opened for this test
		if (!testInstance.getTest().equals(test)) {
			throw new Exception("You do not have permission to access this page.");
		}
		
		// if finish = true calculate points and stuff
		String finish = request.getParameter("finish");
		if (finish != null && finish.equals("true")) {
			responseWrapper.setResponseStatus(ResponseStatus.RESULTS);
			int totalQuestions = test.getQuestions().size();
			// TODO
			// agregate this call
			int totalQuestionAnswred = resultService.findAllSpecific(testInstance, test).size(); 
			
			if (test.getType() == TestType.SURVEY) {
				responseWrapper.setData(new Integer(Math.min((int) (totalQuestionAnswred * 1.0 / totalQuestions * 100), 100)));
				responseWrapper.setDescription("Thank you for completing our survey.");
			}
			else {
				double myPoints = resultService.sumPoints(testInstance, test);
				int totalPoints = test.getTotalPoints();
				responseWrapper.setData(new Integer(Math.min((int) (myPoints / totalPoints * 100), 100)));
				responseWrapper.setDescription(String.format("You have scored %.0f of total %d points. Answred %d / %d. ", myPoints, test.getTotalPoints(), totalQuestionAnswred, totalQuestions));
			}
			
			// TODO
			// to be tested
			session.invalidate(); 
			return responseWrapper;
		}
		
		// call engine
		if (jsonAnswers.size() > 0) {
			saEngine.postAnswer(test, testInstance, jsonAnswers, session);
		}
		
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		return responseWrapper;
	}
	
	private void printJson(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
		}
		catch (Exception e) {
			System.err.println("ObjectMapper Error");
		}
	}
	
	@ResponseBody
	@JsonView(View.Public.class)
	@ExceptionHandler(Exception.class)
	public ResponseWrapper exceptionHandler(Exception ex) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.ERROR);
		responseWrapper.setDescription(ex.toString());
		System.err.println(ex.toString());		
		return responseWrapper;
	}
}
