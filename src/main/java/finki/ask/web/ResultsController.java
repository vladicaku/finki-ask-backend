package finki.ask.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.api.model.ResponseStatus;
import finki.ask.api.model.ResponseWrapper;
import finki.ask.model.ResultInfo;
import finki.ask.model.Test;
import finki.ask.service.ResultInfoService;
import finki.ask.service.TestService;
import finki.ask.view.View;

@RestController
@RequestMapping("/admin/results")
@CrossOrigin
public class ResultsController {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private ResultInfoService resultInfoService;

	@ResponseBody
	@JsonView(View.Public.class)
	@RequestMapping(value="/{id}", produces = "application/json", method = RequestMethod.GET)
	private ResponseWrapper getResults(@PathVariable long id, HttpServletRequest request) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.SUCCESS);
		
		Test test = testService.findById(id);
		List<ResultInfo> results = resultInfoService.getResultInfoForTest(id);
		int incorrect[] = new int[results.size()];
		int partialCorrect[] = new int[results.size()];
		int correct[] = new int[results.size()];
		int counter = 0;
		
		for (ResultInfo r : results) {
			incorrect[counter] = r.getIncorrect();
			partialCorrect[counter] = r.getPartialCorrect();
			correct[counter] = r.getCorrect();
			counter++;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("test", test);
		map.put("results", results);
		map.put("correct", correct);
		map.put("partialCorrect", partialCorrect);
		map.put("incorrect", incorrect);
		
		responseWrapper.setData(map);
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
