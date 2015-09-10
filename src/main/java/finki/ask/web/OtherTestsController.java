package finki.ask.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.api.model.ResponseStatus;
import finki.ask.api.model.ResponseWrapper;
import finki.ask.model.Test;
import finki.ask.model.User;
import finki.ask.service.TestService;
import finki.ask.service.UserService;
import finki.ask.view.View;

@CrossOrigin
@RestController
@RequestMapping("/admin/other/tests")
public class OtherTestsController {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@JsonView(View.SummaryAdmin.class)
	@RequestMapping(produces = "application/json", method = RequestMethod.GET)
	public ResponseWrapper findAllExcept(boolean except, HttpServletRequest request) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		String name = request.getParameter("name");

		// TODO
		// need to be refactored
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    User user = userService.findByUsername(username);
	    
		responseWrapper.setData(testService.findOtherTestsExceptForUser(user));
		
		return responseWrapper;
	}
	
	@RequestMapping(value = "/clone/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Test> clone() {
		return null;
	}
	
	
	
}
