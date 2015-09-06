package finki.ask.service;

import finki.ask.api.model.ResponseWrapper;
import finki.ask.model.TestInstance;

public interface ResultsService {
	public ResponseWrapper getResultsForTestInstance(TestInstance testInstance);
}
