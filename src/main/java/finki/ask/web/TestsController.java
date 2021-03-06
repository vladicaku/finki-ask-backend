package finki.ask.web;

import java.util.List;
import java.util.TimeZone;

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

import finki.ask.api.model.ResponseStatus;
import finki.ask.api.model.ResponseWrapper;
import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.Test;
import finki.ask.service.AnswerService;
import finki.ask.service.QuestionService;
import finki.ask.service.TestService;
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
		//long userId = getUserId(request);

		if (name == null) {
			//return testService.findForUser(userId);
			responseWrapper.setData(testService.findAll());
		} else {
			//return testService.findByNameForUser(name, userId);
			responseWrapper.setData(testService.findAll());
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
		
		try {
			/* This peace of code throws error
			 * when some of the attributes (questionId) is null.
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(test));
			*/
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
		} catch (Exception e) {
			System.err.println(e.toString());
			System.err.println(e.getStackTrace());
			responseWrapper.setDescription(test.toString());
			return responseWrapper;
		}

		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		return responseWrapper;
	}
	
	@ResponseBody
	@JsonView(View.Public.class)
	@RequestMapping(value = "/{id}", consumes = "application/json", method = RequestMethod.POST)
	public ResponseWrapper update(@PathVariable long id, @RequestBody Test test) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		
		// TODO
		// check if this test belongs to the logged user
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

	// @ResponseBody
	// @RequestMapping(value = "/{id}", produces = "application/json", method =
	// RequestMethod.PUT)
//	 public ResponseEntity<String> update(@RequestBody Test test,
//	 @PathVariable long id, HttpServletRequest request, HttpServletResponse
//	 response) {
//	 try{
//	 Test originalTest = testService.findById(id);
//	 long userId = getUserId(request);
//	
//	 if (originalTest.getCreator() != userId) {
//	 return new ResponseEntity<>("You are not allowed to edit this item.",
//	 HttpStatus.UNAUTHORIZED);
//	 }
//	
//	 if (id != test.getId()) {
//	 return new ResponseEntity<>("Wrong url identifie.",
//	 HttpStatus.BAD_REQUEST);
//	 }
//	
//	 test.setCreator(userId);
//	 testService.save(test);
//	 }
//	 catch (Exception e) {
//	 return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//	 }
//	
//	 return new ResponseEntity<>(HttpStatus.OK);
//	 }
	
	
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
