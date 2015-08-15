package finki.ask.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import finki.ask.model.Test;
import finki.ask.service.TestService;

@RestController
@RequestMapping("/admin/other/tests")
public class OtherTestsController {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping(produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public List<Test> findAll(HttpServletRequest request) {
		String parm = request.getParameter("name");
		if (parm != null) {
			return testService.findAll();
		} else {
			return testService.findByName(parm);
		}
	}
	
	@RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Test findById(@PathVariable long id) {
		return testService.findById(id);
	}
	
	@RequestMapping(value = "/clone/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Test> clone() {
		return null;
	}
	
	
	
}
