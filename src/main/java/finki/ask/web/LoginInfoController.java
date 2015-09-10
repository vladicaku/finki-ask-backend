package finki.ask.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.api.model.ResponseStatus;
import finki.ask.api.model.ResponseWrapper;
import finki.ask.model.User;
import finki.ask.service.UserService;
import finki.ask.view.View;

@RestController
@CrossOrigin
@RequestMapping("/admin/login-info")
public class LoginInfoController {
	
	@Autowired
	private UserService userService;

	@ResponseBody
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseWrapper getLoginInfo() {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    //responseWrapper.setData(auth.getName()); //get logged in username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    User user = userService.findByUsername(username);
	    responseWrapper.setData(user);
	    return responseWrapper;
	}
	
	@ResponseBody
	@JsonView(View.Public.class)
	@ExceptionHandler(Exception.class)
	public ResponseWrapper exceptionHandler(Exception ex) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.ERROR);
		responseWrapper.setDescription("Wrong username or password");
		System.err.println(ex.toString());
		ex.printStackTrace();		
		return responseWrapper;
	}
}
