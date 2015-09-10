package finki.ask.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.api.model.ResponseStatus;
import finki.ask.api.model.ResponseWrapper;
import finki.ask.view.View;

//@Controller
//@CrossOrigin
//@RequestMapping("/login")
public class LoginController {

	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;

	@Autowired
	SecurityContextRepository repository;

	@Autowired
	RememberMeServices rememberMeServices;

	@ResponseBody
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseWrapper login(@RequestParam(value = "username", required = true) String username, 
			@RequestParam(value = "password", required = true) String password,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication auth = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(auth);
		repository.saveContext(SecurityContextHolder.getContext(), request, response);
		rememberMeServices.loginSuccess(request, response, auth);
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
