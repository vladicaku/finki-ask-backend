package finki.ask.service.impl;

import org.springframework.stereotype.Service;

import finki.ask.api.model.ResponseStatus;
import finki.ask.api.model.ResponseWrapper;
import finki.ask.model.TestInstance;
import finki.ask.service.ResultsService;

@Service
public class ResultsServiceImpl implements ResultsService{

	@Override
	public ResponseWrapper getResultsForTestInstance(TestInstance testInstance) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setResponseStatus(ResponseStatus.RESULTS);
		
		return responseWrapper;
	}

}
