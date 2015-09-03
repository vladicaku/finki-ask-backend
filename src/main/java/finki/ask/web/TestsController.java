package finki.ask.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.QuestionType;
import finki.ask.model.Test;
import finki.ask.model.TestType;
import finki.ask.service.AnswerService;
import finki.ask.service.QuestionService;
import finki.ask.service.TestService;

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
	@RequestMapping(produces = "application/json", method = RequestMethod.GET)
	public List<Test> findAll(HttpServletRequest request) {
		String name = request.getParameter("name");
		long userId = getUserId(request);

		if (name == null) {
			return testService.findForUser(userId);
		} else {
			return testService.findByNameForUser(name, userId);
		}
	}

	@ResponseBody
	@RequestMapping(consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<Test> create(@RequestBody Test test, HttpServletRequest request, HttpServletResponse response) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(test));
			// long userId = getUserId(request);
			// test.setCreator(userId);
//			System.out.println("<<<<<<  " + test.getName());
//			System.out.println("<<<<<<  " + test.getPassword());
//			System.out.println("<<<<<<  " + test.getType().toString());
//			System.out.println("<<<<<<  " + test.getStart().toString());

			for (Question q : test.getQuestions()) {
				q.setTest(test);
				for (Answer a : q.getAnswers()) {
					a.setQuestion(q);
				}
			}

			testService.save(test);
//			for (Question q : test.getQuestions()) {
//				q.setTest(test);
//				questionService.save(q);
//				for (Answer a : q.getAnswers()) {
//					a.setQuestion(q);
//					answerService.save(a);
//				}
//			}

		} catch (Exception e) {
			System.err.println(e.toString());
			System.err.println(e.getStackTrace());
			return new ResponseEntity<Test>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Test>(HttpStatus.OK);
	}
	//
	// @ResponseBody
	// @RequestMapping(value = "/{id}", produces = "application/json", method =
	// RequestMethod.GET)
	// public ResponseEntity<Test> findById(@PathVariable long id) {
	// try {
	// return new ResponseEntity<>(testService.findById(id), HttpStatus.OK);
	// }
	// catch (Exception ex) {
	// return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	// }
	// }
	//
	// @ResponseBody
	// @RequestMapping(value = "/{id}", produces = "application/json", method =
	// RequestMethod.PUT)
	// public ResponseEntity<String> update(@RequestBody Test test,
	// @PathVariable long id, HttpServletRequest request, HttpServletResponse
	// response) {
	// try{
	// Test originalTest = testService.findById(id);
	// long userId = getUserId(request);
	//
	// if (originalTest.getCreator() != userId) {
	// return new ResponseEntity<>("You are not allowed to edit this item.",
	// HttpStatus.UNAUTHORIZED);
	// }
	//
	// if (id != test.getId()) {
	// return new ResponseEntity<>("Wrong url identifie.",
	// HttpStatus.BAD_REQUEST);
	// }
	//
	// test.setCreator(userId);
	// testService.save(test);
	// }
	// catch (Exception e) {
	// return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
	// }
	//
	// return new ResponseEntity<>(HttpStatus.OK);
	// }

	@ResponseBody
	@RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable long id, HttpServletResponse response) {
		try {
			testService.delete(id);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/create-session", method = RequestMethod.GET)
	@ResponseBody
	public void createSession(HttpServletRequest request) {
		request.getSession(true).setAttribute("userId", 0);
	}

}
