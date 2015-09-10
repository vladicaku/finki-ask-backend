package finki.ask.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.api.model.ResponseStatus;
import finki.ask.api.model.ResponseWrapper;
import finki.ask.exception.InternalException;
import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.Test;
import finki.ask.model.User;
import finki.ask.service.AnswerService;
import finki.ask.service.QuestionService;
import finki.ask.service.TestService;
import finki.ask.service.UserService;
import finki.ask.view.View;

@CrossOrigin
@RestController
@RequestMapping("/admin/tests")
public class TestsController {

	@Autowired
	private TestService testService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private UserService userService;

	private long getUserId(HttpServletRequest request) {
		HttpSession sesion = request.getSession(false);
		return Long.parseLong(sesion.getAttribute("userId").toString());
	}

	@ResponseBody
	@JsonView(View.SummaryAdmin.class)
	@RequestMapping(produces = "application/json", method = RequestMethod.GET)
	public ResponseWrapper findAll(HttpServletRequest request) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		String name = request.getParameter("name");

		// TODO
		// need to be refactored
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    User user = userService.findByUsername(username);
	    
		if (name == null) {
			responseWrapper.setData(testService.findForUser(user));
		} else {
			responseWrapper.setData(testService.findByNameForUser(name, user));
		}
		
		return responseWrapper;
	}
	

	@ResponseBody
	@JsonView(View.Public.class)
	@RequestMapping(consumes = "application/json", method = RequestMethod.POST)
	public ResponseWrapper create(@RequestBody Test test, HttpServletRequest request, HttpServletResponse response) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.ERROR);
		// TODO
		// check if this test belongs to the logged user
		
		/* This peace of code throws error
		 * when some of the attributes (questionId) is null.
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(test));
		*/
		
		// TODO
		// need to be refactored
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    User user = userService.findByUsername(username);
	    
		int totalPoints = 0;
		for (Question q : test.getQuestions()) {
			q.setTest(test);
			totalPoints += q.getPoints();
			for (Answer a : q.getAnswers()) {
				a.setQuestion(q);
			}
		}
		test.setTotalPoints(totalPoints);
		test.setCreator(user);
		testService.save(test);

		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		return responseWrapper;
	}
	
	@ResponseBody
	@JsonView(View.Public.class)
	@RequestMapping(value = "/{id}", consumes = "application/json", method = RequestMethod.POST)
	public ResponseWrapper update(@PathVariable long id, @RequestBody Test test) throws InternalException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		
		// TODO
		// need to be refactored
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    User user = userService.findByUsername(username);
	    
		// check if this test belongs to the logged user
	    Test tempTest = testService.findById(test.getId());
	    if (!tempTest.getCreator().equals(user)) {
	    	throw new InternalException("You don't have permission to edit this test.");
	    }
	    
	    if (test.getId() != id) {
	    	throw new InternalException("Invalid url.");
	    }
	    
			    
		int totalPoints = 0;
		for (Question q : test.getQuestions()) {
			q.setTest(test);
			totalPoints += q.getPoints();
			for (Answer a : q.getAnswers()) {
				a.setQuestion(q);
			}
		}
		test.setTotalPoints(totalPoints);
		testService.save(test);
		
		return responseWrapper;
	}
	
	@ResponseBody
	@JsonView(View.CompleteAdmin.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseWrapper findById(@PathVariable long id) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		//findByNameForUser
		Test test = testService.findById(id);
		for (Question q : test.getQuestions()) {
			System.out.println(q.getId());
		}
		responseWrapper.setData(test);
		return responseWrapper;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public ResponseWrapper delete(@PathVariable long id, HttpServletResponse response) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		testService.delete(id);
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
