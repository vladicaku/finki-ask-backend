package finki.ask.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import finki.ask.json.view.View;
import finki.ask.model.Answer;
import finki.ask.model.Question;
import finki.ask.model.QuestionType;
import finki.ask.model.Test;
import finki.ask.model.TestType;
import finki.ask.service.AnswerService;
import finki.ask.service.QuestionService;
import finki.ask.service.TestService;


@RestController
@RequestMapping("/admin/tests")
@CrossOrigin
public class TestsController {

	@Autowired
	private TestService testService;
	
	@Autowired
	@JsonView(View.TestWithQuestions.class)
	private QuestionService questionService;
	
	@Autowired
	@JsonView(View.TestWithQuestions.class)
	private AnswerService answerService;
	
	private long getUserId(HttpServletRequest request) {
		HttpSession sesion = request.getSession(false);
		return Long.parseLong(sesion.getAttribute("userId").toString());
	}

	@RequestMapping(produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	@JsonView(View.Test.class)
	public List<Test> findAll(HttpServletRequest request) {
		System.out.println("<<<<<< List handler");
		String parm = request.getParameter("name");
		long userId = getUserId(request);
		if (parm == null) {
			return testService.findForUser(userId);
		} else {
			return testService.findByNameForUser(parm, userId);
		}
	}
	
	
	@RequestMapping(consumes = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Test> create(@RequestBody Test test, HttpServletRequest request, HttpServletResponse response) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
		System.out.println("<<<<<< Create handler");
		
		//try {
			//ObjectMapper mapper = new ObjectMapper();
			//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(test));
			//long userId = getUserId(request);
			//test.setCreator(userId);
			System.out.println("<<<<<<  " + test.getName());
			System.out.println("<<<<<<  " + test.getPassword());
			System.out.println("<<<<<<  " + test.getType().toString());
			System.out.println("<<<<<<  " + test.getStart().toString());
			
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
			
//		} catch (Exception e) {
//			return new ResponseEntity<Test>(headers ,HttpStatus.BAD_REQUEST);
//		}
		
		return new ResponseEntity<Test>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	@JsonView(View.TestWithQuestions.class)
	public Test findById(@PathVariable long id) {
		return testService.findById(id);
	}
	
	@RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity update(@RequestBody Test test, @PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
		//try{
			Test originalTest = testService.findById(id);
			long userId = getUserId(request);
			
			if (originalTest.getCreator() != userId) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			test.setCreator(userId);
			testService.save(test);
			// if orphanRemoval works, we will ignore this
//			Set<Question> questionsToBeDeleted = originalTest.getQuestions();
//			
//			for (Question q : test.getQuestions()) {
//				questionsToBeDeleted.remove(q);
//			}
			
//		}
//		catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity delete(@PathVariable long id, HttpServletResponse response) {
		testService.delete(id);
		//response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/create-session", method = RequestMethod.GET)
	@ResponseBody
	public void createSession(HttpServletRequest request) {
		request.getSession(true).setAttribute("userId", 0);
	}
	
	@RequestMapping(value = "/kreirajNovo", method = RequestMethod.GET)
	@ResponseBody
	public void createNew() {
		Test test = new Test();
		test.setName("Nov test");
		test.setType(TestType.OPENSURVEY);
		test.setDateCreated(new Date());
		test.setStart(new Date());
		test.setEnd(new Date());
		test.setDuration(99);
		test.setPassword("password123");
		testService.save(test);
		
//		User user = new User();
//		user.setType(100);
//		
//		test.setCreator(user);
		
		
		Question question = new Question();
		question.setText("Kade se rodil Goce Delcev");
		question.setType(QuestionType.SINGLE);
		question.setTest(test);
		questionService.save(question);
		
		Answer answer1 = new Answer();
		answer1.setText("Kukus");
		answer1.setCorrect(true);
		answer1.setQuestion(question);
		answerService.save(answer1);
		
		Answer answer2 = new Answer();
		answer2.setText("Negde drugde");
		answer2.setCorrect(true);
		answer2.setQuestion(question);
		answerService.save(answer2);
		
		
	}

}
